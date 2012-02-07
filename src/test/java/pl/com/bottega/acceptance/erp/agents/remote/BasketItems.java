package pl.com.bottega.acceptance.erp.agents.remote;

import java.util.HashMap;
import java.util.Map;

import org.jbehave.core.annotations.AfterScenario;

import pl.com.bottega.acceptance.commons.RemoteAgent;

/**
 * Used to keep values between steps.
 * 
 * @author Rafał Jamróz
 */
@RemoteAgent
public class BasketItems {
    private Map<Long, Integer> productsIdsWithCounts = new HashMap<Long, Integer>();

    public Map<Long, Integer> getProductsIdsWithCounts() {
        return productsIdsWithCounts;
    }

    @AfterScenario
    public void clearContext() {
        productsIdsWithCounts = new HashMap<Long, Integer>();
    }
}
