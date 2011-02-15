package org.harper.frm.core.config;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.harper.frm.core.tools.ToolPack;
import org.harper.frm.core.tools.bean.JXPathBeanAccess;


/**
 * Storing all configuration info. <br>
 * TODO Prepare to enable dynamically modification via MBean later
 * 
 * 
 * @author Harper Jiang
 * @since Core 1.0
 * @version 1.0
 */
public class ConfigManager implements ConfigService {

	private static ConfigManager instance;

	public static ConfigManager getInstance() {
		return getInstance(new PropertyConfigProvider());
	}

	public static synchronized ConfigManager getInstance(ConfigProvider provider) {
		if (instance == null) {
			instance = new ConfigManager(provider);
		}
		return instance;
	}

	/**
	 * In case one Provider need to be replaced at runtime
	 */
	public static synchronized void reset() {
		instance = null;
	}

	private ConfigManager(ConfigProvider provider) {
		super();
		setProvider(provider);
	}

	private ConfigProvider provider;

	public ConfigProvider getProvider() {
		return provider;
	}

	public void setProvider(ConfigProvider provider) {
		this.provider = provider;
	}

	private Map<Class<? extends ConfigBean>, ConfigBean> beanCache;

	/**
	 * 
	 * @return
	 */
	public Map<Class<? extends ConfigBean>, ConfigBean> getBeanCache() {
		if (beanCache == null)
			beanCache = Collections
					.synchronizedMap(new HashMap<Class<? extends ConfigBean>, ConfigBean>());
		return beanCache;
	}

	/**
	 * Reload the configuration from supporter. Notify all registered
	 * Configurable that they should also be updated.
	 */
	public void update() {
		//TODO Not Implemented
	}

	/**
	 * 
	 */
	public void config(Configurable configurable) {
		configurable.setConfig(getConfigBean(configurable.getConfigClass()));
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public synchronized <T extends ConfigBean> T getConfigBean(
			Class<T> configBeanClass) {
		Validate.isTrue(ToolPack.subClass(ConfigBean.class, configBeanClass));
		if (getBeanCache().containsKey(configBeanClass))
			return (T) getBeanCache().get(configBeanClass);

		ConfigBean bean;
		try {
			bean = (ConfigBean) configBeanClass.newInstance();
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(
					"ConfigBean class must define an non-argument constructor",
					e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(
					"ConfigBean class must define an non-argument constructor",
					e);
		}
		Properties props = bean.getPairs();
		for (Entry<Object, Object> entry : props.entrySet()) {
			// Try to detect parameter type based on bean attribute
			// type.
			Class<?> type;
			try {
				type = PropertyUtils.getPropertyType(bean, String.valueOf(entry
						.getKey()));
			} catch (InvocationTargetException e) {
				throw new RuntimeException(
						"RuntimeException occurs while accessing the bean", e);
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException(
						"The provide attribute doesn't have an accessible getter:"
								+ entry.getKey());
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException(
						"The provide attribute doesn't have an getter:"
								+ entry.getKey());
			}
			if (type == null)
				throw new IllegalArgumentException(
						"The provide attribute cannot be accessed:"
								+ entry.getKey());
			Object value = null;

			if (Integer.class == type || Integer.TYPE == type)
				try {
					value = StringUtils.isEmpty(bean.getName()) ? provider
							.loadInt(String.valueOf(entry.getValue()))
							: provider.loadInt(bean.getName(), String
									.valueOf(entry.getValue()));
				} catch (KeyNotFoundException e) {
					throw new KeyNotFoundException("Key not found:"
							+ configBeanClass.getName() + "." + entry.getKey());
				}
			else if (Boolean.class == type || Boolean.TYPE == type)
				try {
					value = StringUtils.isEmpty(bean.getName()) ? provider
							.loadBoolean(String.valueOf(entry.getValue()))
							: provider.loadBoolean(bean.getName(), String
									.valueOf(entry.getValue()));
				} catch (KeyNotFoundException e) {
					throw new KeyNotFoundException("Key not found:"
							+ configBeanClass.getName() + "." + entry.getKey());
				}
			else if (String.class == type)
				try {
					value = StringUtils.isEmpty(bean.getName()) ? provider
							.loadString(String.valueOf(entry.getValue()))
							: provider.loadString(bean.getName(), String
									.valueOf(entry.getValue()));
				} catch (KeyNotFoundException e) {
					throw new KeyNotFoundException("Key not found:"
							+ configBeanClass.getName() + "." + entry.getKey());
				}
			else if (ToolPack.subClass(ConfigBean.class, type))
				value = getConfigBean(type.asSubclass(ConfigBean.class));
			else
				throw new UnsupportedOperationException("Type not supported:"
						+ type.getName());
			JXPathBeanAccess.set(bean, String.valueOf(entry.getKey()), value);
		}
		beanCache.put(configBeanClass, bean);
		return (T) bean;
	}

	/**
	 * 
	 */
	public String getConfigValue(Class<? extends ConfigBean> configBeanClass,
			String configName) {
		return JXPathBeanAccess.get(getConfigBean(configBeanClass), configName)
				.toString();
	}

	/**
	 * Old style API for those config keys that not be easy to classify.
	 * 
	 * @param configKey
	 * @return
	 * @throws KeyNotFoundException
	 *             if the given key cannot be retrieved.
	 */
	public String getConfigValue(String configKey) {
		return getProvider().loadString(configKey);
	}
}
