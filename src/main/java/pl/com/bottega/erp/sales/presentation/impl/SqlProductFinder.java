package pl.com.bottega.erp.sales.presentation.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import pl.com.bottega.cqrs.query.annotations.Finder;
import pl.com.bottega.ddd.domain.sharedcernel.Money;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;

@Finder
public class SqlProductFinder implements ProductFinder {

    @Inject
    private NamedParameterJdbcOperations jdbcTemplate;

    @Override
    public List<ProductListItemDto> findProducts(ProductSearchCriteria searchCriteria) {
        final String QUERY = "SELECT id, name, value, currencyCode FROM Product";
        // where search criteria ...
        Map<String, Object> map = new HashMap<String, Object>();
        return jdbcTemplate.query(QUERY, map, new ProductListItemRowMapper());
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
