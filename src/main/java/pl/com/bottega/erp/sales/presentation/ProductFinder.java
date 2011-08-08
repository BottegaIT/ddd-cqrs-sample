/**
 * 
 */
package pl.com.bottega.erp.sales.presentation;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Slawek
 * 
 */
public interface ProductFinder {

    List<ProductListItemDto> findProducts(ProductSearchCriteria searchCriteria);

    List<ProductListItemDto> findProductsByIds(Collection<Long> productsIds);
}
