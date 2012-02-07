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
