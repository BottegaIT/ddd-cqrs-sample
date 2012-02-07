package pl.com.bottega.cqrs.command;

/**
 * Main access point to the Application.<br>
 * It handles:
 * <ul>
 * <li>filtering command duplicates
 * <li>command queues for asynchronous commands 
 * </ul>
 * 
 * @author Slawek
 *
 */
public interface Gate {

	public abstract Object dispatch(Object command);

}