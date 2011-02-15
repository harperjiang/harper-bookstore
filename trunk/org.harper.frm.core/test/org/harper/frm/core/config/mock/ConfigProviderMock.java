package org.harper.frm.core.config.mock;

import org.harper.frm.core.config.ConfigProvider;
import org.harper.frm.core.config.KeyNotFoundException;

public class ConfigProviderMock implements ConfigProvider {

	public String loadString(String key) {
		if("NULL_VALUE".equals(key))
			return null;
		if("EXCEPTION".equals(key))
			throw new KeyNotFoundException("exception");
		return "value" + key;
	}

	public boolean loadBoolean(String key) {
		if("EXCEPTION".equals(key))
			throw new KeyNotFoundException("exception");
		return true;
	}

	public int loadInt(String key) {
		if("EXCEPTION".equals(key))
			throw new KeyNotFoundException("exception");
		return 3;
	}

	public boolean loadBoolean(String header, String key) {
		return loadBoolean(key);
	}

	public int loadInt(String header, String key) throws KeyNotFoundException {
		return loadInt(key);
	}

	public String loadString(String header, String key)
			throws KeyNotFoundException {
		return loadString(key);
	}

}
