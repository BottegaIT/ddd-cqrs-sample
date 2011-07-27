/**
 * 
 */
package pl.com.bottega.erp.sales.presentation;

import java.util.List;

/**
 * @author Slawek
 * 
 */
public interface ProductFinder {

    public List<ProductListItemDto> findProducts(ProductSearchCriteria searchCriteria);
}
