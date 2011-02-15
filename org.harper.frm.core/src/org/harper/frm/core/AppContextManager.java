package org.harper.frm.core;

import java.util.HashMap;

import org.harper.frm.core.logging.LogManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Load, cache and manage all spring configuration files.
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since com.oocl.frm.core 1.0
 */
public class AppContextManager {

	private static final String DEFAULT_MODULE_CONFIG = "ivofrm_modules.xml"; //$NON-NLS-1$

	private static HashMap<String, ApplicationContext> contexts;

	protected static HashMap<String, ApplicationContext> getContexts() {
		if (contexts == null)
			contexts = new HashMap<String, ApplicationContext>();
		return contexts;
	}

	public static ApplicationContext getDefaultContext() {
		return getContext(DEFAULT_MODULE_CONFIG);
	}

	/**
	 * Load a context, and cache it for later usage.
	 * 
	 * @param name
	 *            Context file name, must be located in current classpath.
	 * @return Loaded Context, null if nothing found.
	 */
	public synchronized static ApplicationContext getContext(String name) {
		try {
			if (getContexts().containsKey(name))
				return (ApplicationContext) getContexts().get(name);
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					name);
			getContexts().put(name, context);
			return context;
		} catch (Exception e) {
			LogManager.getInstance().getLogger(AppContextManager.class).error(
					"Error while loading context configuration:" + name, e); //$NON-NLS-1$
			return null;
		}
	}
}
