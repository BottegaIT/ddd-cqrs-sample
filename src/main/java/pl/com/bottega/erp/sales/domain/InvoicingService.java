/*
 * Copyright 2011-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.com.bottega.erp.sales.domain;

import javax.inject.Inject;

import pl.com.bottega.ddd.domain.annotations.DomainService;
import pl.com.bottega.ddd.domain.sharedkernel.Money;

/**
 * Sample Domain Service that contains logic that:
 * <ul>
 * <li> Does not have a natural place in any aggregate - we don't want to bloat Order with issuance of the Invoice
 * <li> Has broad dependencies - we don't want Order to become a 'God Class'
 * <li> Is used only (or not many) in one Use Case/user Story so is not essential for any Aggregate
 * <ul>
 * 
 * Notice that this Domain Service is managed by Container in order to be able to inject dependencies like Repo  
 * 
 * @author Slawek
 *
 */
@DomainService
public class InvoicingService {
	
	@Inject
	private ProductRepository productRepository;
	
	public Invoice issuance(Order order, TaxPolicy taxPolicy){
		//TODO refactor to InvoiceFactory
		Invoice invoice = new Invoice(order.getClient());
		
		for (OrderedProduct orderedProduct : order.getOrderedProducts()){
			Product product = productRepository.load(orderedProduct.getProductId());			
			
			Money net = orderedProduct.getEffectiveCost();			
			Tax tax = taxPolicy.calculateTax(product.getType(), net);			
						
			InvoiceLine invoiceLine = new InvoiceLine(product, orderedProduct.getQuantity(), net, tax);			
			invoice.addItem(invoiceLine);
		}
		
		return invoice;
	}
	
}
