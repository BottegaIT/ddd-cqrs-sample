package pl.com.bottega.acceptance.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class SimpleMapScope implements Scope {

	/**
	 * This map contains for each bean name or ID the created object. The
	 * objects are created with a spring object factory.
	 */
	private final Map<String, Object> objectMap = new HashMap<String, Object>();

	/**
	 * {@inheritDoc}
	 */
	public Object get(String theName, ObjectFactory<?> theObjectFactory) {
		Object object = objectMap.get(theName);
		if (null == object) {
			object = theObjectFactory.getObject();
			objectMap.put(theName, object);
		}
		return object;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getConversationId() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public void registerDestructionCallback(final String theName,
			final Runnable theCallback) {
		// nothing to do ... this is optional and not required
	}

	/**
	 * {@inheritDoc}
	 */
	public Object remove(final String theName) {
		return objectMap.remove(theName);
	}

	@Override
	public Object resolveContextualObject(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
