package org.harper.frm.core.config;


/**
 * ConfigProvider takes resp to load configuration from different resources.
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since Core 1.0
 */
public interface ConfigProvider {

	public String loadString(String key) throws KeyNotFoundException;

	public int loadInt(String key) throws KeyNotFoundException;

	public boolean loadBoolean(String key) throws KeyNotFoundException;

	public String loadString(String header, String key)
			throws KeyNotFoundException;

	public int loadInt(String header, String key) throws KeyNotFoundException;

	public boolean loadBoolean(String header, String key);
}
