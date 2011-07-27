/**
 * 
 */
package pl.com.bottega.cqrs.command.handler;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Slawek
 *
 */
@Component
@Transactional
//@Secured
public @interface CommandHandlerAnnotation {

}
