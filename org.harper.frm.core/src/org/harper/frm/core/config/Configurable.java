package org.harper.frm.core.config;

/**
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since Core 1.0
 * @see ConfigManager
 */
public interface Configurable {

	public Class<? extends ConfigBean> getConfigClass();

	public ConfigBean getConfig();

	public void setConfig(ConfigBean config);

	/**
	 * Callback method invoked by the {@link ConfigManager} when configuration
	 * value changed.
	 */
	public void configUpdate();
}
