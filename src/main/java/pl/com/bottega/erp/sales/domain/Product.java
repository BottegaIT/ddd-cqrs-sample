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
