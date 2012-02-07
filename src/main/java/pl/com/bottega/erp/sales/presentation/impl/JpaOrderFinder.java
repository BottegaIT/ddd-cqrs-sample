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
package pl.com.bottega.erp.sales.presentation.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import pl.com.bottega.cqrs.query.annotations.Finder;
import pl.com.bottega.erp.sales.domain.Order;
import pl.com.bottega.erp.sales.presentation.ClientOrderDetailsDto;
import pl.com.bottega.erp.sales.presentation.ClientOrderListItemDto;
import pl.com.bottega.erp.sales.presentation.OrderFinder;

/**
 * @author Rafał Jamróz
 */
@Finder
public class JpaOrderFinder implements OrderFinder {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ClientOrderDetailsDto getClientOrderDetails(Long orderId) {
        Order order = entityManager.find(Order.class, orderId);
        return order == null ? null : toOrderDetailsDto(order);
    }

    private ClientOrderDetailsDto toOrderDetailsDto(Order order) {
        ClientOrderDetailsDto dto = new ClientOrderDetailsDto();
        dto.setOrderId(order.getEntityId());
        dto.setTotalCost(order.getTotalCost());
        dto.setOrderedProducts(order.getOrderedProducts());
        dto.setSubmitDate(order.getSubmitDate());
        dto.setOrderStatus(order.getStatus());
        return dto;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ClientOrderListItemDto> findOrders() {
        Query query = entityManager
                .createQuery("select new pl.com.bottega.erp.sales.presentation.ClientOrderListItemDto("
                        + "o.id, o.totalCost, o.submitDate, o.status) from Order o");
        return query.getResultList();
    }
}
