package org.harper.frm.core.mbean;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * The MBean implementation of {@link LogControlMBean}
 * 
 * @author Harper
 * @since Core 1.0
 * @version 1.0 Jul 14, 2009
 * 
 */
public class LogControl implements LogControlMBean {

	public String getLogLevel(String loggerName) {
		Logger logger = Logger.getLogger(loggerName);
		return logger.getLevel().toString();
	}

	public void setLogLevel(String loggerName, String levelName) {
		Validate.notNull(levelName);
		Level lev = Level.toLevel(levelName);
		if (null != lev)
			Logger.getLogger(loggerName).setLevel(lev);
	}

}
