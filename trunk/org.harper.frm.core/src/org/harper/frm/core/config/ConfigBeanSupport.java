package org.harper.frm.core.config;

import java.lang.reflect.Field;
import java.util.Properties;

import org.harper.frm.core.tools.ToolPack;


/**
 * Base class for {@link ConfigBean}s
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since Core 1.0
 * @see ConfigBean
 */
public abstract class ConfigBeanSupport implements ConfigBean {

	private String name;

	private Properties pairs;

	public String getName() {
		if (name == null) {
			ConfigBeanInfo info = (ConfigBeanInfo) getClass().getAnnotation(
					ConfigBeanInfo.class);
			name = (info == null ? null : info.name());
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Properties getPairs() {
		if (pairs == null) {
			pairs = new Properties();
		}
		for (Field f : getClass().getDeclaredFields()) {
			if (f.isAnnotationPresent(ConfigKey.class)) {
				ConfigKey configKey = f.getAnnotation(ConfigKey.class);
				if (configKey != null) {
					pairs.put(f.getName(), configKey.value());
				}
			} else if (f.isAnnotationPresent(NestedBean.class)
					&& ToolPack.subClass(ConfigBean.class, f.getType())) {
				NestedBean nestedBean = f.getAnnotation(NestedBean.class);
				if (nestedBean != null) {
					pairs.put(f.getName(), f.getType());
				}
			}
		}
		return pairs;
	}
}
