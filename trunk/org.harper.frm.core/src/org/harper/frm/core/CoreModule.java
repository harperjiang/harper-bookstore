package org.harper.frm.core;

import java.util.Properties;

import org.harper.frm.core.config.ConfigManager;
import org.harper.frm.core.config.ConfigService;
import org.harper.frm.core.module.AbstractModule;
import org.harper.frm.core.module.ModuleInitException;


/**
 * Core Module
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since Core 1.0
 */
public class CoreModule extends AbstractModule {

	public CoreModule() {
		super();
	}

	private static CoreModule module = null;

	public static CoreModule getDefault() {
		return module;
	}

	/**
	 * Init the Core Module
	 */
	protected void initModule() throws ModuleInitException {
		module = this;

		// Register Platform Services
		registerService(PlatformInfo.class.getName(),
				new Oc4j1013PlatformInfo(), null);
		// Register Configuration Services
		Properties configprop = new Properties();
		configprop.put("Source", "Properties");
		registerService(ConfigService.class.getName(), ConfigManager
				.getInstance(), configprop);
	}

	protected void destoryModeule() {
		module = null;
	}
}