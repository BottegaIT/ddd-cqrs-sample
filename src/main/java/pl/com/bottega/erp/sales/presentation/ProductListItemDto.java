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
/**
 * 
 */
package pl.com.bottega.erp.sales.presentation;

import java.io.Serializable;

import pl.com.bottega.ddd.domain.sharedkernel.Money;

/**
 * @author Slawek
 * 
 */
public class ProductListItemDto implements Serializable {
    private Long productId;
    private String displayedName;
    private Money price;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public void setDisplayedName(String productDisplayedName) {
        this.displayedName = productDisplayedName;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }
}
