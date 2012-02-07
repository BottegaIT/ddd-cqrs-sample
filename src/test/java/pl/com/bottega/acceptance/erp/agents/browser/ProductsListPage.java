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
package pl.com.bottega.acceptance.erp.agents.browser;

import javax.inject.Inject;

import pl.com.bottega.acceptance.commons.BrowserAgent;
import pl.com.bottega.acceptance.commons.agents.browser.BrowserAgentDriver;
import pl.com.bottega.acceptance.erp.agents.ProductsListAgent;

@BrowserAgent
public class ProductsListPage implements ProductsListAgent {

    @Inject
    private BrowserAgentDriver driver;

    @Override
    public boolean productsExist() {
        ensureOnCurrentPage();
        return !driver.elements("#productsTable tbody tr").isEmpty();
    }

    @Override
    public void addAnyProductToBasket() {
        ensureOnCurrentPage();
        driver.element("tbody td.buttonsColumn input[type=button]").click();
    }

    @Override
    public int getBasketItemsCount() {
        ensureOnCurrentPage();
        return driver.elements("#basketItemsList>li").size();
    }

    @Override
    public void checkout() {
        ensureOnCurrentPage();
        driver.element(".basketContainer input[type=submit]").click();
    }

    private void ensureOnCurrentPage() {
        if (!thisIsCurrentPage()) {
            navigateToThisPage();
        }
    }

    private boolean thisIsCurrentPage() {
        return "sales/products/list".equalsIgnoreCase(driver.getCurrentPagePath());
    }

    private void navigateToThisPage() {
        driver.navigateTo("sales/products/list");
    }
}
