package org.harper.frm;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.lang.Validate;
import org.harper.frm.core.AppContextManager;
import org.harper.frm.core.logging.LogManager;
import org.harper.frm.core.module.ModuleInitException;
import org.harper.frm.core.module.ModulePool;
import org.springframework.context.ApplicationContext;


/**
 * Invoking the only method in this class will start up the whole framework.
 * 
 * @author Harper Jiang
 * @version 1.1
 */

public class FrmStarter extends HttpServlet {

	private static final long serialVersionUID = -9098236455692520909L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String configFile = null;
		if ((configFile = config
				.getInitParameter(FrmConfigConstants.CONFIG_FILE_PATH)) != null)
			start(configFile);
		else
			start();
	}

	public void destroy() {
		stop();
		super.destroy();
	}

	private static boolean inited = false;

	protected static ModulePool pool = null;

	/**
	 * Initialize and start up the framework.
	 */
	public synchronized static void start() {
		if (inited)
			return;
		try {
			ApplicationContext appContext = AppContextManager
					.getDefaultContext();
			pool = (ModulePool) appContext.getBean("modulePool");
			pool.initModules();
			inited = true;
			LogManager.getInstance().getGlobalLogger().info(
					Version.getVersionInfo() + " initialized");
		} catch (Exception e) {
			LogManager.getInstance().getGlobalLogger().fatal(
					"Module Init Failed", e);
			throw new ModuleInitException("Module Init Failed", e);
		}
	}

	public synchronized static void start(String configFileName) {
		Validate.notNull(configFileName);
		if (inited)
			return;
		try {
			ApplicationContext appContext = AppContextManager
					.getContext(configFileName);
			pool = (ModulePool) appContext.getBean("modulePool");
			pool.initModules();
			inited = true;
		} catch (Exception e) {
			LogManager.getInstance().getGlobalLogger().fatal(
					"Module Init Failed", e);
			throw new ModuleInitException("Module Init Failed", e);
		}
	}

	public synchronized static void stop() {
		if (!inited)
			return;
		try {
			pool.destroyModules();
			inited = false;
			LogManager.getInstance().getGlobalLogger().info(
					Version.getVersionInfo() + " exit.");
		} catch (Exception e) {
			LogManager.getInstance().getGlobalLogger().fatal(
					"Module Destroy Failed", e);
		}
	}
}
