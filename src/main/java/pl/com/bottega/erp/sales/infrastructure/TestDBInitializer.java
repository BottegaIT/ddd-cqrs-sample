package pl.com.bottega.erp.sales.infrastructure;

import java.io.PrintWriter;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.derby.drda.NetworkServerControl;

/**
 * Initializes test derby database.
 *
 */
@Singleton
@Startup
@Deprecated // Only for testing
public class TestDBInitializer 
{

	@PostConstruct
	public void initialize()
	{
		  try {
		  final NetworkServerControl control = new NetworkServerControl("localhost", "1528");

			control.start(new PrintWriter(System.out));
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
