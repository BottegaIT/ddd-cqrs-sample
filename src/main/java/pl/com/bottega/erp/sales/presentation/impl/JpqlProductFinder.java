package pl.com.bottega.erp.sales.presentation.impl;

import org.apache.commons.lang.StringUtils;
import pl.com.bottega.cqrs.query.PaginatedResult;
import pl.com.bottega.cqrs.query.annotations.Finder;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria.ProductSearchOrder;
import pl.com.bottega.erp.sales.webui.AddSampleProductsOnStartup;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Product finder implementation is based on JPQL.
 */
@Finder
public class JpqlProductFinder implements ProductFinder {


    private static final int MAX_ITEMS_PER_PAGE = 50;

    @PersistenceContext(unitName="defaultPU")
    EntityManager em;


    @Inject // We need to hook up this initialization somewhere, and due to some problems with JPA
            // implementations we cannot rely on javax.ejb.Startup
            AddSampleProductsOnStartup initializer;


    @Override
    @SuppressWarnings("unchecked")
    public PaginatedResult<ProductListItemDto> findProducts(
            ProductSearchCriteria criteria) {


        initializer.addSampleProductsToRepo();

        int productsCount = countProducts(criteria);
        if (productsCount <= 0) {
            return new PaginatedResult<ProductListItemDto>(criteria.getPageNumber(), criteria.getItemsPerPage());
        }

        Map<String, Object> parameters = new HashMap<String, Object>();
        String sqlQuery = createSqlSelectQuery(criteria, parameters);
        int offset = criteria.getItemsPerPage() * (criteria.getPageNumber() - 1);
        int limit = Math.min(criteria.getItemsPerPage(), MAX_ITEMS_PER_PAGE);

        Query query = fillParameters(em.createQuery(sqlQuery), parameters);
        List<ProductListItemDto> products = query.setFirstResult(offset).setMaxResults(limit).getResultList();

        return new PaginatedResult<ProductListItemDto>(products, criteria.getPageNumber(), criteria.getItemsPerPage(), productsCount);
    }

    private int countProducts(ProductSearchCriteria criteria)
    {
        StringBuilder queryStringBuilder = new StringBuilder();

        Map<String, Object> parameters = new HashMap<String, Object>();

        queryStringBuilder.append("SELECT count(p) FROM Product p ");
        appendWhereClause(queryStringBuilder, criteria, parameters);
        Query counterQuery = em.createQuery(queryStringBuilder.toString());
        counterQuery = fillParameters(counterQuery, parameters);
        Long count = (Long)counterQuery.getSingleResult();
        return count.intValue();
    }

    private static Query fillParameters(Query query,  Map<String, Object> parameters)
    {
        Query resultQuery = query;
        for (Entry<String, Object> parameter : parameters.entrySet())
        {
            resultQuery = resultQuery.setParameter(parameter.getKey(), parameter.getValue());
        }
        return resultQuery;

    }
    private String createSqlSelectQuery(ProductSearchCriteria criteria, Map<String, Object> parameters) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT new ")
                .append(ProductListItemDto.class.getName())
                .append("(p.entityId, p.name, p.price) FROM Product p ");
        appendWhereClause(query, criteria, parameters);
        appendOrderByClause(query, criteria);
        return query.toString();
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
            return "p.price.value";
        } else {
            throw new IllegalArgumentException("unknow order by in ProductSearchCriteria");
        }    }

    private static void appendWhereClause(StringBuilder query, ProductSearchCriteria criteria, Map<String, Object> parameters) {
        List<String> constraints = new ArrayList<String>();
        addConstraints(constraints, criteria, parameters);

        if (!constraints.isEmpty()) {
            query.append("WHERE ");
            query.append(StringUtils.join(constraints, " AND "));
            query.append(' ');
        }
    }

    private static void addConstraints(List<String> constraints, ProductSearchCriteria criteria, Map<String, Object> parameters) {
        if (!StringUtils.isBlank(criteria.getContainsText())) {
            constraints.add("LOWER(p.name) LIKE :searchedText");
            parameters.put("searchedText", "%" + criteria.getContainsText().toLowerCase() + "%");
        }
        if (criteria.getMaxPrice() != null) {
            constraints.add("p.price.value <= :maxPrice");
            parameters.put("maxPrice", criteria.getMaxPrice());
        }
        if (criteria.hasSpecificProductIdsFilter()) {
            constraints.add("p.entityId IN (:productIds)");
            parameters.put("productIds", criteria.getSpecificProductIds());
        }
    }

}
