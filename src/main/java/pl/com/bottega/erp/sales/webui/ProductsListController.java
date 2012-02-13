package pl.com.bottega.erp.sales.webui;

import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import pl.com.bottega.cqrs.query.PaginatedResult;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;

@ManagedBean(name="products")
@RequestScoped
public class ProductsListController {

    private static final int RESULTS_PER_PAGE = 10;
    private static final int START_PAGE = 1;

    private ProductSearchCriteria searchCriteria = new ProductSearchCriteria();
    private PaginatedResult<ProductListItemDto> finderResult;

	@Inject
	private ProductFinder finder;

	private int pageNumber()
	{

		FacesContext facesContext = FacesContext.getCurrentInstance();
		String param = (String) facesContext.getExternalContext().getRequestParameterMap().get("page");
		if (param == null)
		{
			return START_PAGE;
		}
		try
		{
			Integer intParam = Integer.parseInt(param);
			return intParam;
		}
		catch (NumberFormatException e)
		{
			return START_PAGE;
		}

	}

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

    public int getPagesCount()
    {
        if (finderResult == null)
        {
            fetch();
        }

        return finderResult.getPagesCount();
    }

	private void fetch()
	{
		searchCriteria.setPageNumber(pageNumber());
        searchCriteria.setItemsPerPage(RESULTS_PER_PAGE);
		finderResult = finder.findProducts(searchCriteria);
	}


    public boolean isAscending()
    {
        return searchCriteria.isAscending();
    }

    public ProductSearchCriteria.ProductSearchOrder getOrderBy()
    {
        return searchCriteria.getOrderBy();
    }

    public ProductSearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    public void setFinderResult(PaginatedResult<ProductListItemDto> finderResult) {
        this.finderResult = finderResult;
    }

    public void sortByName()
    {
        if (ProductSearchCriteria.ProductSearchOrder.NAME.equals(searchCriteria.getOrderBy()))
        {
            searchCriteria.setAscending(!searchCriteria.isAscending());

        }
        searchCriteria.setOrderBy(ProductSearchCriteria.ProductSearchOrder.NAME);
    }

    public PaginatedResult<ProductListItemDto> getFinderResult() {
        return finderResult;
    }

    public void sortByPrice()
    {
        if (ProductSearchCriteria.ProductSearchOrder.PRICE.equals(searchCriteria.getOrderBy()))
        {
            searchCriteria.setAscending(!searchCriteria.isAscending());
        }
        searchCriteria.setOrderBy(ProductSearchCriteria.ProductSearchOrder.PRICE);
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
