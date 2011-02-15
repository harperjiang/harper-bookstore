package org.harper.frm.core.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Configuration providers that read config from properties.
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since Core 1.0
 */
public class PropertyConfigProvider implements ConfigProvider {

	private static final String PROP_NAME = "frm.properties";

	private Map<String, Properties> instances = new HashMap<String, Properties>();

	protected synchronized final Properties getProperties(String header) {
		if (!instances.containsKey(header)) {
			try {
				Properties instance = new Properties();
				if (!header.endsWith("properties"))
					header = header + ".properties";
				instance.load(getClass().getClassLoader().getResourceAsStream(
						header));
				instances.put(header, instance);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return instances.get(header);
	}

	public boolean loadBoolean(String key) throws KeyNotFoundException {
		return loadBoolean(PROP_NAME, key);
	}

	public int loadInt(String key) throws KeyNotFoundException {
		return loadInt(PROP_NAME, key);
	}

	public String loadString(String key) throws KeyNotFoundException {
		return loadString(PROP_NAME, key);
	}

	public boolean loadBoolean(String header, String key)
			throws KeyNotFoundException {
		return Boolean.parseBoolean(getProperties(header).getProperty(key));
	}

	public int loadInt(String header, String key) throws KeyNotFoundException {
		try {
			return Integer.parseInt(getProperties(header).getProperty(key));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Key should be with a number value:" + key);
		}
	}

	public String loadString(String header, String key)
			throws KeyNotFoundException {
		return getProperties(header).getProperty(key);
	}

}
