/**
 * 
 */
package pl.com.bottega.ddd.application.annotation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Slawek
 * 
 */
@Service
@Transactional
//@Secured
public @interface ApplicationService {
    String value() default "";
}
