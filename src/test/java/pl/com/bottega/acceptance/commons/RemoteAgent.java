package pl.com.bottega.acceptance.commons;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate classes as required in "Remote" agent profile.
 * 
 * @see BrowserAgent for more details
 * @author Rafał Jamróz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RemoteAgent {
}
