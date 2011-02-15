package org.harper.frm.core.config;

import org.apache.commons.lang.Validate;

/**
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since Core 1.0
 */
public abstract class ConfigurableSupport implements Configurable {

	private ConfigBean config;

	public ConfigurableSupport() {
		super();
		ConfigManager.getInstance().config(this);
	}

	public ConfigBean getConfig() {
		return config;
	}

	public void setConfig(ConfigBean config) {
		Validate.isTrue(getConfigClass().isInstance(config));
		this.config = config;
	}

	public void configUpdate() {
		// By default do nothing
	}

}
