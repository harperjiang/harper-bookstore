package org.harper.frm.core.logging;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.harper.frm.core.CoreModule;
import org.harper.frm.core.PlatformInfo;
import org.harper.frm.core.config.ConfigBean;
import org.harper.frm.core.config.ConfigurableSupport;
import org.harper.frm.core.logging.ns.TNS;


/**
 * The class <code>LogManager</code> is responsible for providing prepared
 * <code>Log</code> objects for modules.
 * 
 * TODO Is that necessary to change the LogManager to a service?
 * 
 * @author Harper Jiang
 * @since Core 1.0
 * @version 1.0
 */
public class LogManager extends ConfigurableSupport {

	private Log blankLogger;

	public synchronized Log getBlankLogger() {
		if (blankLogger == null)
			blankLogger = new BlankLog();
		return blankLogger;
	}

	private static final Map<String, String> streamMap = new Hashtable<String, String>();

	public static synchronized final String resolve(String streamName) {
		if (streamMap.containsKey(streamName))
			return streamMap.get(streamName);

		PlatformInfo info = null;
		try {
			info = CoreModule.getDefault().getService(PlatformInfo.class);
		} catch (Exception e) {
			info = null;
		}

		if (info == null)
			return streamName;

		String newName = streamName;
		if (info != null)
			newName = info.inCluster() ? info.currentJvmNo() + "." + streamName
					: streamName;
		streamMap.put(streamName, newName);
		return newName;
	}

	public Log getGlobalLogger() {
		if (getConfigBean().isDisableLog())
			return getBlankLogger();
		return LogFactory.getLog(resolve(TNS.NS_FRM));
	}

	public Log getDevelopLogger() {
		if (getConfigBean().isDisableLog())
			return getBlankLogger();
		return LogFactory.getLog(resolve(TNS.DEVELOP));
	}

	public Log getLogger(Class<?> clazz) {
		if (getConfigBean().isDisableLog())
			return getBlankLogger();
		return LogFactory.getLog(resolve(clazz.getName()));
	}

	public Log getLogger(String loggerName) {
		if (getConfigBean().isDisableLog())
			return getBlankLogger();
		return LogFactory.getLog(resolve(loggerName));
	}

	public Class<? extends ConfigBean> getConfigClass() {
		return LogConfigBean.class;
	}

	protected LogConfigBean getConfigBean() {
		return (LogConfigBean) super.getConfig();
	}

	protected LogManager() {
		super();
	}

	private static LogManager manager;

	public static synchronized LogManager getInstance() {
		if (manager == null)
			manager = new LogManager();
		return manager;
	}
}
