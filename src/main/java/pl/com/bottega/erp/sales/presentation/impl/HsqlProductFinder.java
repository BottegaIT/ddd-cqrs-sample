package pl.com.bottega.erp.sales.presentation.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import pl.com.bottega.cqrs.query.annotations.Finder;
import pl.com.bottega.ddd.domain.sharedcernel.Money;
import pl.com.bottega.erp.sales.presentation.PaginatedResult;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria.ProductSearchOrder;

/**
 * TODO finders philosophy description
 * 
 * @author Rafał Jamróz
 */
@Finder
public class HsqlProductFinder implements ProductFinder {

    private static final int MAX_ITEMS_PER_PAGE = 50;

    @Inject
    private NamedParameterJdbcOperations jdbcTemplate;

    @Override
    public PaginatedResult<ProductListItemDto> findProducts(ProductSearchCriteria criteria) {
        int productsCount = countProducts(criteria);
        if (productsCount <= 0) {
            return new PaginatedResult<ProductListItemDto>(criteria.getPageNumber(), criteria.getItemsPerPage());
        }
        Map<String, Object> parameters = new HashMap<String, Object>();
        StringBuilder query = new StringBuilder();
        createSqlSelectQuery(query, criteria, parameters);
        List<ProductListItemDto> products = jdbcTemplate.query(query.toString(), parameters,
                new ProductListItemRowMapper());
        return new PaginatedResult<ProductListItemDto>(products, criteria.getPageNumber(), criteria.getItemsPerPage(),
                productsCount);
    }

    private int countProducts(ProductSearchCriteria criteria) {
        StringBuilder query = new StringBuilder();
        Map<String, Object> parameters = new HashMap<String, Object>();
        query.append("SELECT count(*) from Product ");
        appendWhereClause(query, criteria, parameters);
        return jdbcTemplate.queryForInt(query.toString(), parameters);
    }

    private void createSqlSelectQuery(StringBuilder query, ProductSearchCriteria criteria,
            Map<String, Object> parameters) {
        query.append("SELECT id, name, value, currencyCode FROM Product ");
        appendWhereClause(query, criteria, parameters);
        appendOrderByClause(query, criteria);
        appendOffsetLimitClause(query, criteria, parameters);
    }

    private void appendOffsetLimitClause(StringBuilder query, ProductSearchCriteria criteria,
            Map<String, Object> parameters) {
        int offset = criteria.getItemsPerPage() * (criteria.getPageNumber() - 1);
        int limit = Math.min(criteria.getItemsPerPage(), MAX_ITEMS_PER_PAGE);
        query.append("OFFSET :offset LIMIT :limit ");
        parameters.put("limit", limit);
        parameters.put("offset", offset);

    }

    private void appendWhereClause(StringBuilder query, ProductSearchCriteria criteria, Map<String, Object> parameters) {
        List<String> constraints = new ArrayList<String>();
        addConstraints(constraints, criteria, parameters);

        if (!constraints.isEmpty()) {
            query.append("WHERE ");
            query.append(StringUtils.join(constraints, " AND "));
            query.append(' ');
        }
    }

    private void addConstraints(List<String> constraints, ProductSearchCriteria criteria, Map<String, Object> parameters) {
        if (!StringUtils.isBlank(criteria.getContainsText())) {
            constraints.add("LCASE(name) like :searchedText");
            parameters.put("searchedText", "%" + criteria.getContainsText().toLowerCase() + "%");
        }
        if (criteria.getMaxPrice() != null) {
            constraints.add("value <= :maxPrice");
            parameters.put("maxPrice", criteria.getMaxPrice());
        }
    }

    private void appendOrderByClause(StringBuilder query, ProductSearchCriteria criteria) {
        if (criteria.getOrderBy() != null) {
            query.append("ORDER BY ");
            query.append(getOrderByColumnName(criteria.getOrderBy()));
            query.append(criteria.isAscending() ? " ASC " : " DESC ");
        }
    }

    private String getOrderByColumnName(ProductSearchOrder orderBy) {
        if (ProductSearchOrder.NAME.equals(orderBy)) {
            return "name";
        } else if (ProductSearchOrder.PRICE.equals(orderBy)) {
            return "value";
        } else {
            throw new IllegalArgumentException("unknow order by in ProductSearchCriteria");
        }
    }

    @Override
    public List<ProductListItemDto> findProductsByIds(Collection<Long> productsIds) {
        if (productsIds.isEmpty()) {
            return Collections.emptyList();
        }
        String sql = "SELECT p.id, p.name, p.value, p.currencyCode FROM Product p WHERE p.id in ("
                + StringUtils.join(productsIds, ", ") + ")";
        // String productsIdsString = ;
        // Collections.singletonMap("productsIds", productsIdsString);
        return jdbcTemplate.query(sql, new HashMap<String, Object>(), new ProductListItemRowMapper());
    }

    private static class ProductListItemRowMapper implements RowMapper<ProductListItemDto> {

        @Override
        public ProductListItemDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProductListItemDto dto = new ProductListItemDto();
            dto.setProductId(rs.getLong("id"));
            dto.setDisplayedName(rs.getString("name"));
            dto.setPrice(new Money(rs.getBigDecimal("value"), Currency.getInstance(rs.getString("currencyCode"))));
            return dto;
        }
    }
}
