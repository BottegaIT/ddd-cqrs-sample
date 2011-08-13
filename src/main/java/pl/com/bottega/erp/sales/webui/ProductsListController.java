package pl.com.bottega.erp.sales.webui;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.com.bottega.cqrs.command.Gate;
import pl.com.bottega.erp.sales.application.ClientBasket;
import pl.com.bottega.erp.sales.application.commands.CreateOrderCommand;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria.ProductSearchOrder;

@Controller
@RequestMapping("/sales/products")
public class ProductsListController {

    @Inject
    private ProductFinder productFinder;
    @Inject
    private ClientBasket basket;
    @Inject
    private Gate gate;

    private static final int RESULTS_PER_PAGE = 10;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String productsList(Model model, @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "ascending", required = false) Boolean ascending,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "containsText", required = false) String containsText) {

        ProductSearchCriteria criteria = new ProductSearchCriteria();
        addFiltering(criteria, containsText, maxPrice);
        addPagination(criteria, page, RESULTS_PER_PAGE);
        addOrdering(criteria, sortBy, ascending);

        model.addAttribute("products", productFinder.findProducts(criteria));
        model.addAttribute("sortBy", criteria.getOrderBy().toString());
        model.addAttribute("ascending", criteria.isAscending());
        return "sales/productsList";
    }

    private void addFiltering(ProductSearchCriteria criteria, String containsText, Double maxPrice) {
        criteria.setContainsText(containsText);
        criteria.setMaxPrice(maxPrice);
    }

    private void addPagination(ProductSearchCriteria criteria, Integer page, int resultsPerPage) {
        if (page != null) {
            criteria.setPageNumber(page);
        }
        criteria.setItemsPerPage(RESULTS_PER_PAGE);
    }

    private void addOrdering(ProductSearchCriteria criteria, String sortBy, Boolean ascending) {
        if (sortBy != null) {
            criteria.setOrderBy(ProductSearchOrder.valueOf(sortBy));
        }
        if (ascending != null) {
            criteria.setAscending(ascending);
        }
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String createOrder() {
        Long orderId = (Long) gate.dispatch(new CreateOrderCommand(basket.getProductIdsWithCounts()));
        return "redirect:/sales/confirmOrder/" + orderId;
    }
}
