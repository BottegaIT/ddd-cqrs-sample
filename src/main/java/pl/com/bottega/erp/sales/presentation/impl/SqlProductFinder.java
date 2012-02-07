/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.com.bottega.erp.sales.presentation.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import pl.com.bottega.cqrs.query.PaginatedResult;
import pl.com.bottega.cqrs.query.annotations.Finder;
import pl.com.bottega.ddd.domain.sharedkernel.Money;
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
public class SqlProductFinder implements ProductFinder {

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
        String sqlQuery = createSqlSelectQuery(criteria, parameters);
        List<ProductListItemDto> products = jdbcTemplate.query(sqlQuery, parameters, new ProductListItemRowMapper());
        return new PaginatedResult<ProductListItemDto>(products, criteria.getPageNumber(), criteria.getItemsPerPage(),
                productsCount);
    }

    private int countProducts(ProductSearchCriteria criteria) {
        StringBuilder query = new StringBuilder();
        Map<String, Object> parameters = new HashMap<String, Object>();
        query.append("SELECT count(*) FROM Product p ");
        appendWhereClause(query, criteria, parameters);
        return jdbcTemplate.queryForInt(query.toString(), parameters);
    }

    private String createSqlSelectQuery(ProductSearchCriteria criteria, Map<String, Object> parameters) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT p.entityId, p.name, p.value, p.currencyCode FROM Product p ");
        appendWhereClause(query, criteria, parameters);
        appendOrderByClause(query, criteria);
        appendOffsetLimitClause(query, criteria, parameters);
        return query.toString();
    }

    private void appendOffsetLimitClause(StringBuilder query, ProductSearchCriteria criteria,
            Map<String, Object> parameters) {
        int offset = criteria.getItemsPerPage() * (criteria.getPageNumber() - 1);
        int limit = Math.min(criteria.getItemsPerPage(), MAX_ITEMS_PER_PAGE);
        query.append("OFFSET :offset LIMIT :limit ");
        parameters.put("offset", offset);
        parameters.put("limit", limit);

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
            constraints.add("LOWER(p.name) LIKE :searchedText");
            parameters.put("searchedText", "%" + criteria.getContainsText().toLowerCase() + "%");
        }
        if (criteria.getMaxPrice() != null) {
            constraints.add("p.value <= :maxPrice");
            parameters.put("maxPrice", criteria.getMaxPrice());
        }
        if (criteria.hasSpecificProductIdsFilter()) {
            constraints.add("p.entityId IN (:productIds)");
            parameters.put("productIds", criteria.getSpecificProductIds());
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
            return "p.name";
        } else if (ProductSearchOrder.PRICE.equals(orderBy)) {
            return "p.value";
        } else {
            throw new IllegalArgumentException("unknow order by in ProductSearchCriteria");
        }
    }

    private static class ProductListItemRowMapper implements RowMapper<ProductListItemDto> {

        @Override
        public ProductListItemDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProductListItemDto dto = new ProductListItemDto();
            dto.setProductId(rs.getLong("entityId"));
            dto.setDisplayedName(rs.getString("name"));
            dto.setPrice(new Money(rs.getBigDecimal("value"), Currency.getInstance(rs.getString("currencyCode"))));
            return dto;
        }
    }
}
