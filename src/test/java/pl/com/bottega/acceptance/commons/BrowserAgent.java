package pl.com.bottega.acceptance.commons;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate classes as required in "Browser" agent profile.
 * 
 * Agents work as proxy that connects to a specific interface of the application
 * that can be tested. Examples of agents: web browser or a remote protocol
 * (like XML or JSon over HTTP, AMF, web services, etc.)
 * 
 * Technical comment: Spring components are created for classes with this
 * annotation thanks to AgentComponentFilter. If JBehave supported Spring 3.1
 * then a combination of @Component and @Profile would be much better.
 * 
 * @author Rafał Jamróz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BrowserAgent {
}
