/**
 * 
 */
package pl.com.bottega.erp.sales.presentation;

import pl.com.bottega.cqrs.query.PaginatedResult;

/**
 * @author Slawek
 * 
 */
public interface ProductFinder {

    PaginatedResult<ProductListItemDto> findProducts(ProductSearchCriteria searchCriteria);
}
