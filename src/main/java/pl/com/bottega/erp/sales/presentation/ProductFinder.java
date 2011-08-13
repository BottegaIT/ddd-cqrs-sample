/**
 * 
 */
package pl.com.bottega.erp.sales.presentation;

/**
 * @author Slawek
 * 
 */
public interface ProductFinder {

    PaginatedResult<ProductListItemDto> findProducts(ProductSearchCriteria searchCriteria);
}
