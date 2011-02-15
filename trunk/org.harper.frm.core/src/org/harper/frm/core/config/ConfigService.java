/**
 * 
 */
package org.harper.frm.core.config;


/**
 * @author Harper Jiang
 * @version 1.0
 * @since Core 1.0
 */
public interface ConfigService {
	/**
	 * Retrieve Configuration value by the given key
	 * 
	 * @param configKey
	 * @return configuration value
	 */
	public String getConfigValue(String configKey);

	/**
	 * Retrieve configuration info by the given class and attribute.
	 * 
	 * @param configBeanClass
	 *            ConfigBean class
	 * @param configName
	 *            attribute info
	 * @return configuration value
	 */
	public String getConfigValue(Class<? extends ConfigBean> configBeanClass,
			String configName);

	/**
	 * Get configuration bean instance by the given class
	 * 
	 * @param <T>
	 *            bean type
	 * @param configBeanClass
	 *            bean class
	 * @return bean with config value loaded.
	 */
	public <T extends ConfigBean> T getConfigBean(Class<T> configBeanClass);

	/**
	 * Config the given configurable instance
	 * 
	 * @param configurable
	 *            configurable object
	 */
	public void config(Configurable configurable);
}
