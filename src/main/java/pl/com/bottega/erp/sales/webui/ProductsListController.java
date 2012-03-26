package pl.com.bottega.erp.sales.webui;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import com.sun.mail.imap.protocol.SearchSequence;

import pl.com.bottega.cqrs.query.PaginatedResult;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria.ProductSearchOrder;

@ManagedBean(name="products")
@SessionScoped
public class ProductsListController {

	@Inject
	private ProductFinder finder;

	private ProductSearchCriteria searchCriteria = new ProductSearchCriteria();

	private PaginatedResult<ProductListItemDto> finderResult = new PaginatedResult<ProductListItemDto>(START_PAGE, 0);

	private static final int RESULTS_PER_PAGE = 10;
	private static final int START_PAGE = 1;


	public boolean isAscending()
	{
		return searchCriteria.isAscending();
	}

    public ProductSearchCriteria getSearchCriteria()
    {
        return searchCriteria;
    }

    public void setSearchCriteria(ProductSearchCriteria searchCriteria)
    {
        this.searchCriteria = searchCriteria;
    }

	public ProductSearchOrder getOrderBy()
	{
		return searchCriteria.getOrderBy();
	}
	public List<ProductListItemDto> getItems()
	{
        return finderResult.getItems();
	}

    public List<Integer> getPages()
    {
        return finderResult.getPages();
    }

    public void loadAction()
    {
        fetch();
    }

	public int getTotalItemsCount()
	{
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
        return finderResult.getPagesCount();
	}

	public String getContainsTextFilter()
	{
		return searchCriteria.getContainsText();
	}

	public void setContainsTextFilter(String containsText)
	{
		if (containsText != null) {
			searchCriteria.setContainsText(containsText.trim());
		}
	}

	public void setMaxPriceFilter(Double d)
	{
		searchCriteria.setMaxPrice(d);
	}

	public Double getMaxPriceFilter()
	{
		return searchCriteria.getMaxPrice();
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
