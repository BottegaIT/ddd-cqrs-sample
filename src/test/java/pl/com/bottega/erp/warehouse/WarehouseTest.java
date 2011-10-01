package pl.com.bottega.erp.warehouse;

import static junit.framework.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.com.bottega.cqrs.command.Gate;
import pl.com.bottega.cqrs.query.PaginatedResult;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;
import pl.com.bottega.erp.warehouse.application.PrepareParcelCommand;
import pl.com.bottega.erp.warehouse.presentation.ParcelDto;
import pl.com.bottega.erp.warehouse.presentation.ParcelFinder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/functionalTestsContext.xml")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class WarehouseTest {

    @Inject
    private Gate gate;

    @Inject
    private ParcelFinder packageFinder;

    @Test
    public void shouldCreatePackageWithAvailableProducts() throws Exception {
        // given
        Set<Long> existingProductsId = anyAvailableProducts(2);
        String address = "adres";
        // when
        Long packageId = createPackage(existingProductsId, address);
        // then
        ParcelDto parcel = packageFinder.get(packageId);
        assertEquals("COMPLETE", parcel.getStatus());
        assertEquals("adres", parcel.getAddress());
        assertEquals(existingProductsId, parcel.getProductsIds());
    }

    private Long createPackage(Set<Long> existingProductsId, String address) {
        return (Long) gate.dispatch(new PrepareParcelCommand(existingProductsId, address));
    }

    @Inject
    private ProductFinder productFinder;

    private Set<Long> anyAvailableProducts(int count) {
        Set<Long> result = new HashSet<Long>();
        PaginatedResult<ProductListItemDto> findProducts = productFinder.findProducts(new ProductSearchCriteria());
        List<ProductListItemDto> items = findProducts.getItems();
        for (int i = 0; i < count; i++) {
            result.add(items.get(i).getProductId());
        }
        return result;
    }
}
