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
package pl.com.bottega.erp.sales.application.events;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import pl.com.bottega.ddd.application.annotation.ApplicationEvent;

/**
 * 
 * @author Slawek
 * 
 */
@ApplicationEvent
@SuppressWarnings("serial")
public class ProductAddedToOrderEvent implements Serializable {

    private Long productid;

    private Long clientId;

    private int quantity;

    public ProductAddedToOrderEvent(Long productid, Long clientId, int quantity) {
        this.productid = productid;
        this.clientId = clientId;
        this.quantity = quantity;
    }

    public Long getProductid() {
        return productid;
    }

    public Long getClientId() {
        return clientId;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
