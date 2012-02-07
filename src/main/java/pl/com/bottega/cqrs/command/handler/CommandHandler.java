/**
 * 
 */
package pl.com.bottega.cqrs.command.handler;

import pl.com.bottega.cqrs.command.Command;

/**
 * 
 * @author Slawek
 *
 * @param <C> command
 * @param <R> result type - for asynchronous {@link Command}commands (asynchronous=true) should be {@link Void}
 */
public interface CommandHandler<C, R> {

    public R handle(C command);
}
