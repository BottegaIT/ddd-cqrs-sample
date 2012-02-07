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
import pl.com.bottega.acceptance.erp.agents.OrderConfirmationAgent;

@BrowserAgent
public class OrderConfirmationPage implements OrderConfirmationAgent {

    private static final String PAGE_URL_REGEXP = "^sales/confirmOrder/\\d+$";

    @Inject
    private BrowserAgentDriver driver;

    public int getProductsCount() {
        ensureOnCurrentPage();
        return driver.elements(".ordersTable tbody>tr").size();
    }

    public void submit() {
        ensureOnCurrentPage();
        driver.element("#orderConfirmationForm input[type=submit]").click();
    }

    @Override
    public boolean isSubmitted() {
        ensureOnCurrentPage();
        return "SUBMITTED".equals(driver.element(".orderConfirmationStatus").getText());
    }

    private void ensureOnCurrentPage() {
        if (!isCurrentPage()) {
            throw new RuntimeException("not on order confirmation page");
        }
    }

    private boolean isCurrentPage() {
        return driver.getCurrentPagePath().matches(PAGE_URL_REGEXP);
    }
}
