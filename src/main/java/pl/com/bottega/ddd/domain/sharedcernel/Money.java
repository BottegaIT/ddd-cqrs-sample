package pl.com.bottega.ddd.domain.sharedcernel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

import javax.persistence.Embeddable;

import pl.com.bottega.ddd.domain.annotations.ValueObject;

/**
 * 
 * @author Slawek
 * 
 */
@SuppressWarnings("serial")
@Embeddable
@ValueObject
public class Money implements Serializable {

    public static final Currency DEFAULT_CURRENCY = Currency.getInstance("EUR");

    public static final Money ZERO = new Money(0.0);

    private BigDecimal value;

    private String currencyCode;

    protected Money() {
    }

    public Money(BigDecimal value, Currency currency) {
        this(value, currency.getCurrencyCode());
    }
    
    private Money(BigDecimal value, String currencyCode) {
        this.value = value;
        this.currencyCode = currencyCode;
    }

    public Money(BigDecimal value) {
        this(value, DEFAULT_CURRENCY);
    }

    public Money(double value, Currency currency) {
        this(value, currency.getCurrencyCode());
    }
    
    private Money(double value, String currencyCode) {
        this.value = new BigDecimal(value);
        this.currencyCode = currencyCode;
    }


    public Money(double value) {
        this(value, DEFAULT_CURRENCY);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Money) {
			Money money = (Money) obj;
			return value.equals(money.value) && currencyCode.equals(money.currencyCode);
		}
        
        return false;
    }

    public Money multiplyBy(double multiplier) {
        return multiplyBy(new BigDecimal(multiplier));
    }

    public Money multiplyBy(BigDecimal multiplier) {
        return new Money(value.multiply(multiplier), currencyCode);
    }

    public Money add(Money money) {
        if (!currencyCode.equals(money.getCurrencyCode()))
            throw new IllegalArgumentException("Currency mismatch");

        return new Money(value.add(money.value), currencyCode);
    }

    public Money subtract(Money money) {
        if (!currencyCode.equals(money.getCurrencyCode()))
            throw new IllegalArgumentException("Currency mismatch");

        return new Money(value.subtract(money.value), currencyCode);
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
    
    public Currency getCurrency() {
        return Currency.getInstance(currencyCode);
    }

    public boolean greaterThan(Money other) {
        return value.compareTo(other.value) > 0;
    }

    public boolean lessThan(Money other) {
        return value.compareTo(other.value) < 0;
    }

    /**
     * @param debt
     * @return
     */
    public boolean lessOrEquals(Money other) {
        return value.compareTo(other.value) <= 0;
    }

    @Override
    public String toString() {
        return value.toPlainString() + getCurrency().getSymbol();
    }

}
