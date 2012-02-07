package pl.com.bottega.acceptance.commons.agents.browser;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import pl.com.bottega.acceptance.commons.BrowserAgent;

import com.google.common.base.Function;

@BrowserAgent
public class BrowserAgentDriver extends WebDriverPage {

    //@Value("${contextPath}")
    private String contextPath;

    //@Value("${serverUrl}")
    private String serverUrl;

    private static final long DEFAULT_TIMEOUT_IN_SECONDS = 2;

    @Inject
    public BrowserAgentDriver(WebDriverProvider driverProvider) {
        super(driverProvider);
    }

    // helper functions and shortcuts for selectors
    /**
     * Returns element by CSS selector. If it doen't exists it will wait for it
     * to appear for up to 2 seconds.
     * 
     * @throws TimeoutException
     *             if no element appears during that time
     */
    public WebElement element(String cssSelector) {
        return hang(DEFAULT_TIMEOUT_IN_SECONDS).until(elementAppears(cssSelector));
    }

    /**
     * Returns elements by CSS selector. If no elements exists it will for wait
     * until at least 1 element appears or 2 seconds elapse.
     */
    public List<WebElement> elements(String cssSelector) {
        try {
            hang(DEFAULT_TIMEOUT_IN_SECONDS).until(elementAppears(cssSelector));
        } catch (TimeoutException e) {
            // ignore
        }
        return findElements(By.cssSelector(cssSelector));
    }

    private WebDriverWait hang(long timeoutInSeconds) {
        return new WebDriverWait(this, timeoutInSeconds);
    }

    private Function<WebDriver, WebElement> elementAppears(final String cssSelector) {
        return new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                WebElement element = driver.findElement(By.cssSelector(cssSelector));
                if (element.isDisplayed()) {
                    return element;
                }
                throw new NotFoundException();
            }
        };
    }

    // navigation
    public void navigateTo(String path) {
        navigate().to(serverUrl + "/" + contextPath + "/" + path);
    }

    public String getCurrentPagePath() {
        String currentPath = getCurrentPageURI().getPath();
        currentPath = stripSlashes(currentPath);
        if (StringUtils.startsWithIgnoreCase(currentPath, contextPath)) {
            currentPath = StringUtils.stripStart(currentPath, contextPath);
            return stripSlashes(currentPath);
        } else {
            return "";
        }
    }

    private String stripSlashes(String currentPath) {
        return StringUtils.strip(currentPath, "/");
    }

    private URI getCurrentPageURI() {
        try {
            return new URI(getCurrentUrl());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}