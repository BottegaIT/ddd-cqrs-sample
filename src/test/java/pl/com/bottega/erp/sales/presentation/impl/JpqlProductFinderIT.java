package pl.com.bottega.erp.sales.presentation.impl;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.com.bottega.cqrs.query.PaginatedResult;
import pl.com.bottega.erp.sales.domain.Client;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;
import pl.com.bottega.erp.sales.webui.AddSampleProductsOnStartup;

@RunWith(Arquillian.class)
public class JpqlProductFinderIT {

	@Deployment
	public static Archive<?> createTestArchive()
	{
		return ShrinkWrap.create(JavaArchive.class, JpqlProductFinderIT.class.getName() + ".jar").addPackages(false,
				JpqlProductFinder.class.getPackage()).addClasses(pl.com.bottega.erp.sales.domain.Product.class, AddSampleProductsOnStartup.class,
						Client.class)
				.addAsManifestResource(new ByteArrayAsset("<beans />".getBytes()), ArchivePaths.create("beans.xml"))
				.addAsManifestResource("META-INF/persistence.xml", "persistence.xml");
	}

	@Inject
	JpqlProductFinder finder;
	
	@Test
	public void limitsPageSize()
	{
		// given
		ProductSearchCriteria criteria = new ProductSearchCriteria();
		criteria.setItemsPerPage(4);
		criteria.setPageNumber(1);
		// when
		PaginatedResult<ProductListItemDto> result = finder.findProducts(criteria);
		
		// then
		Assert.assertEquals(4, result.getItems().size());
	}
	
	@Test
	public void filtersByName()
	{
		// given
		ProductSearchCriteria criteria = new ProductSearchCriteria();
		criteria.setItemsPerPage(100);
		criteria.setPageNumber(1);
		criteria.setContainsText("Zombies");
		// when
		PaginatedResult<ProductListItemDto> result = finder.findProducts(criteria);
		
		// then
		Assert.assertEquals(20, result.getItems().size());
	}
	
	@Test
	public void filtersByMaxPrice()
	{
		// given
		ProductSearchCriteria criteria = new ProductSearchCriteria();
		criteria.setMaxPrice(Double.valueOf(20));
		criteria.setPageNumber(1);
		// when
		PaginatedResult<ProductListItemDto> result = finder.findProducts(criteria);
		
		// then
		Assert.assertEquals(40, result.getItems().size());
	}

}
