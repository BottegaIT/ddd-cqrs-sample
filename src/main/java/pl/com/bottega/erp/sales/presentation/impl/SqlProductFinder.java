package pl.com.bottega.erp.sales.presentation.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
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
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;

/**
 * TODO finders philosophy description
 * 
 * @author Rafał Jamróz
 */
@Finder
public class SqlProductFinder implements ProductFinder {

    @Inject
    private NamedParameterJdbcOperations jdbcTemplate;

    @Override
    public List<ProductListItemDto> findProducts(ProductSearchCriteria searchCriteria) {
        StringBuilder query = new StringBuilder();
        Map<String, Object> parameters = new HashMap<String, Object>();
        query.append("SELECT id, name, value, currencyCode FROM Product");
        query.append(createWhereClause(searchCriteria, parameters));
        query.append(createOrderClause(searchCriteria));
        return jdbcTemplate.query(query.toString(), parameters, new ProductListItemRowMapper());
    }

    private String createWhereClause(ProductSearchCriteria searchCriteria, Map<String, Object> parameters) {
        return "";
    }

    private String createOrderClause(ProductSearchCriteria searchCriteria) {
        return "";
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
