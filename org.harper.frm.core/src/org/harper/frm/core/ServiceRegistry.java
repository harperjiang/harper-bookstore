package org.harper.frm.core;

import java.util.Hashtable;
import java.util.Map;

/**
 * A temporary solution before migrate to OSGi architecture.
 * 
 * @author Harper Jiang
 * 
 */
public class ServiceRegistry {

	private static Map<String, Object> cache = new Hashtable<String, Object>();

	private static final int ERROR_THRESHOLD = 20;

	public synchronized static void register(String name, Object object) {
		if (cache.size() >= ERROR_THRESHOLD)
			throw new RuntimeException("Registry is full!");
		cache.put(name, object);
	}

	public synchronized static void unregister(String name) {
		cache.remove(name);
	}

	public synchronized static Object getService(String name) {
		return cache.get(name);
	}

}
