package pl.com.bottega.ddd.domain.sharedkernel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Currency;

import org.junit.Test;

import pl.com.bottega.ddd.domain.sharedkernel.Money;

public class MoneyTest {

    private static final Currency USD = Currency.getInstance("USD");
    private static final Currency EUR = Currency.getInstance("EUR");

    @Test
    public void shouldAddDefaultCurrency() throws Exception {
        assertEquals(new Money(3.27), new Money(1.14).add(new Money(2.13)));
    }

    @Test
    public void shouldAddNonDefaultCurrency() throws Exception {
        assertEquals(new Money(4.00, USD), new Money(1.99, USD).add(new Money(2.01, USD)));
    }

    @Test
    public void shouldSameValueAndCurrencyBeEquals() throws Exception {
        assertEquals(new Money(1.99, USD), new Money(1.99, USD));
    }

    @Test
    public void shouldSameValueAndDifferentCurrencyBeNonEqual() throws Exception {
        assertFalse(new Money(1.99, USD).equals(new Money(1.99, EUR)));
    }

    @Test
    public void shouldInstanceof() throws Exception {
        assertFalse(null instanceof Money);
    }
}
