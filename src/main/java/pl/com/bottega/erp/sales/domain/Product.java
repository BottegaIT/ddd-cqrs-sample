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

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import pl.com.bottega.ddd.domain.BaseEntity;
import pl.com.bottega.ddd.domain.annotations.DomainEntity;
import pl.com.bottega.ddd.domain.sharedkernel.Money;

/**
 * 
 * @author Slawek
 * 
 */
@Entity
@DomainEntity
public class Product extends BaseEntity {

    public enum ProductType {
        DRUG, FOOD, STANDARD
    }

    @Enumerated(EnumType.STRING)
    private ProductType type;

    private Money price;

    private String name;

    protected Product() {
    }

    public Product(ProductType type, Money price, String name) {
        this.type = type;
        this.price = price;
        this.name = name;
    }

    public Money getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public ProductType getType() {
        return type;
    }

}
