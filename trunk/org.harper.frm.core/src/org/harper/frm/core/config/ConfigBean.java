package org.harper.frm.core.config;

import java.util.Properties;

/**
 * 
 * @author Harper Jiang
 * @since Core 1.0 (Old Common)
 * @version 1.0
 */
public interface ConfigBean {

	/**
	 * Get the name of the configuration bean
	 * 
	 * @return name string of the bean
	 */
	public String getName();

	/**
	 * 
	 * @return Properties containing the mapping between JXPath string and
	 *         Configuration key
	 */
	public Properties getPairs();
}
