package pl.com.bottega.erp.sales.webui;

import pl.com.bottega.cqrs.query.PaginatedResult;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria.ProductSearchOrder;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.util.List;

@ManagedBean(name="products")
@SessionScoped
public class ProductsListController {

	@Inject
	private ProductFinder finder;

	private ProductSearchCriteria searchCriteria = new ProductSearchCriteria();
	private PaginatedResult<ProductListItemDto> finderResult;

	private static final int RESULTS_PER_PAGE = 10;

	public List<ProductListItemDto> getItems()
	{
		fetch();
		return finderResult.getItems();
	}

	public int getTotalItemsCount()
	{
		if (finderResult == null)
		{
			fetch();
		}

		return finderResult.getTotalItemsCount();
	}

	public void sortByName()
	{
		if (ProductSearchOrder.NAME.equals(searchCriteria.getOrderBy()))
		{
			searchCriteria.setAscending(!searchCriteria.isAscending());

		}
		searchCriteria.setOrderBy(ProductSearchOrder.NAME);
	}

	public void sortByPrice()
	{
		if (ProductSearchOrder.PRICE.equals(searchCriteria.getOrderBy()))
		{
			searchCriteria.setAscending(!searchCriteria.isAscending());
		}
		searchCriteria.setOrderBy(ProductSearchOrder.PRICE);
	}

	private void fetch()
	{
		searchCriteria.setItemsPerPage(RESULTS_PER_PAGE);
		finderResult =  finder.findProducts(searchCriteria);
	}

	public int getPagesCount()
	{
		if (finderResult == null)
		{
			fetch();
		}

		return finderResult.getPagesCount();
	}

    public ProductSearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

 	public void addToOrder(Long productId)
	{

	}

	public String doFilter()
	{
		return null;
	}

	public String clearFilter()
	{
		searchCriteria = new ProductSearchCriteria();
		finderResult = null;
		return null;
	}
}
