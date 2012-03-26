package pl.com.bottega.erp.sales;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.junit.Assert.assertEquals;

import java.util.Collections;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.com.bottega.TestEventPublisher;
import pl.com.bottega.cqrs.command.Gate;
import pl.com.bottega.cqrs.command.handler.cdi.CDIHandlersProvider;
import pl.com.bottega.cqrs.command.impl.RunEnvironment;
import pl.com.bottega.cqrs.command.impl.StandardGate;
import pl.com.bottega.ddd.application.SystemUser;
import pl.com.bottega.ddd.domain.support.InjectorHelper;
import pl.com.bottega.erp.sales.application.commands.AddProductToOrderCommand;
import pl.com.bottega.erp.sales.application.commands.CreateOrderCommand;
import pl.com.bottega.erp.sales.application.commands.SubmitOrderCommand;
import pl.com.bottega.erp.sales.application.commands.handlers.AddProductToOrderCommandHandler;
import pl.com.bottega.erp.sales.application.commands.handlers.CreateOrderCommandHandler;
import pl.com.bottega.erp.sales.application.commands.handlers.SubmitOrderCommandHandler;
import pl.com.bottega.erp.sales.domain.Client;
import pl.com.bottega.erp.sales.domain.Invoice;
import pl.com.bottega.erp.sales.domain.InvoiceLine;
import pl.com.bottega.erp.sales.domain.InvoicingService;
import pl.com.bottega.erp.sales.domain.Order;
import pl.com.bottega.erp.sales.domain.Order.OrderStatus;
import pl.com.bottega.erp.sales.domain.OrderFactory;
import pl.com.bottega.erp.sales.domain.OrderLine;
import pl.com.bottega.erp.sales.domain.OrderedProduct;
import pl.com.bottega.erp.sales.domain.Product;
import pl.com.bottega.erp.sales.domain.RebatePolicyFactory;
import pl.com.bottega.erp.sales.domain.events.OrderCreatedEvent;
import pl.com.bottega.erp.sales.domain.events.OrderSubmittedEvent;
import pl.com.bottega.erp.sales.infrastructure.repositories.jpa.JpaClientRepository;
import pl.com.bottega.erp.sales.infrastructure.repositories.jpa.JpaInvoiceRepository;
import pl.com.bottega.erp.sales.infrastructure.repositories.jpa.JpaOrderRepository;
import pl.com.bottega.erp.sales.infrastructure.repositories.jpa.JpaProductRepository;
import pl.com.bottega.erp.sales.presentation.ClientOrderDetailsDto;
import pl.com.bottega.erp.sales.presentation.OrderFinder;
import pl.com.bottega.erp.sales.presentation.ProductFinder;
import pl.com.bottega.erp.sales.presentation.ProductListItemDto;
import pl.com.bottega.erp.sales.presentation.ProductSearchCriteria;
import pl.com.bottega.erp.sales.presentation.impl.JpaOrderFinder;
import pl.com.bottega.erp.sales.presentation.impl.JpqlProductFinder;
import pl.com.bottega.erp.sales.webui.AddSampleProductsOnStartup;

/**
 * Functional tests for ordering products by clients.
 * 
 * @author Rafał Jamróz
 * @author Krzysztof Ciesielski
 */

@RunWith(Arquillian.class)
public class ProductsOrderingIT {

	@Inject
	private ProductFinder productFinder;

	@Inject
	private Gate gate;

	@Inject
	private OrderFinder orderFinder;

	@Inject
	TestEventPublisher events;

	@Deployment
	public static JavaArchive createTestArchive()
	{
		return ShrinkWrap.create(JavaArchive.class, "test.jar").addClasses( 
				JpqlProductFinder.class, StandardGate.class, JpaOrderFinder.class, RunEnvironment.class, CDIHandlersProvider.class,
				Product.class, Client.class, CreateOrderCommandHandler.class, OrderFactory.class, RebatePolicyFactory.class, InjectorHelper.class,
				TestEventPublisher.class, SystemUser.class, JpaOrderRepository.class,
				JpaProductRepository.class, JpaClientRepository.class, Order.class, OrderLine.class,
				AddSampleProductsOnStartup.class, AddProductToOrderCommandHandler.class,
				SubmitOrderCommandHandler.class, JpaInvoiceRepository.class, InvoicingService.class, Invoice.class, InvoiceLine.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"))
				.addAsManifestResource("META-INF/persistence.xml", "persistence.xml");
	}

	@Before
	public void prepare() throws Exception
	{

		events.reset();
	}

	@After
	public void cleanup() throws Exception
	{
	}

	@Test
	public void shouldCreateOrder() throws Exception {
		// given
		Long existingProductId = anyProduct().getProductId();
		// when
		Long unsubmittedOrderId = createOrderWithProduct(existingProductId);
		// then
		events.expect(new OrderCreatedEvent(unsubmittedOrderId));
		assertUnconfirmedOrderExistsWithProduct(unsubmittedOrderId, existingProductId);
	}

	@Test
	public void shouldSubmitCreatedOrder() throws Exception {
		// given
		Long existingProductId = anyProduct().getProductId();
		// when
		Long orderId = createOrderWithProduct(existingProductId);

		submitOrder(orderId);

		// then
		assertEquals(OrderStatus.SUBMITTED, orderFinder.getClientOrderDetails(orderId).getOrderStatus());
		events.expect(new OrderCreatedEvent(orderId), new OrderSubmittedEvent(orderId));
	}

	@Test
	public void shouldAddAlreadyAddedProdctByIncreasingQuantity(){
		//given
		Long existingProductId = anyProduct().getProductId();
		//when
		Long orderId = createOrderWithProduct(existingProductId);
		addProduct(existingProductId, orderId);
		//then
		assertEquals(1, orderFinder.getClientOrderDetails(orderId).getOrderedProducts().size());
	}

	@Test
	public void shouldAddProdct(){
		//given
		Long existingProductId = anyProduct().getProductId();
		Long otherProductId = anyOtherProduct().getProductId();
		//when
		Long orderId = createOrderWithProduct(existingProductId);
		addProduct(otherProductId, orderId);
		//then
		assertEquals(2, orderFinder.getClientOrderDetails(orderId).getOrderedProducts().size());
	}



	// helper methods
	// given
	private ProductListItemDto anyProduct() {
		return productFinder.findProducts(new ProductSearchCriteria()).getItems().get(0);
	}
	private ProductListItemDto anyOtherProduct() {
		return productFinder.findProducts(new ProductSearchCriteria()).getItems().get(1);
	}

	// when
	private Long createOrderWithProduct(Long productId) {
		CreateOrderCommand command = new CreateOrderCommand(Collections.singletonMap(productId, 1));
		return (Long) gate.dispatch(command);
	}

	private void submitOrder(Long orderId) {
		gate.dispatch(new SubmitOrderCommand(orderId));
	}

	private void addProduct(Long productId, Long orderId) {
		AddProductToOrderCommand command = new AddProductToOrderCommand(orderId, productId, 1);
		gate.dispatch(command);
	}

	// then
	private void assertUnconfirmedOrderExistsWithProduct(Long orderId, Long productId) {
		ClientOrderDetailsDto order = orderFinder.getClientOrderDetails(orderId);
		OrderedProduct onlyProduct = getOnlyElement(order.getOrderedProducts());
		assertEquals(OrderStatus.DRAFT, order.getOrderStatus());
		assertEquals(productId, onlyProduct.getProductId());
	}
}
