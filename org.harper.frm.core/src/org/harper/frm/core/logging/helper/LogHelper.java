package org.harper.frm.core.logging.helper;

import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

/**
 * Helper used to provide some compatibility between JDK logging and Log4j
 * logging.
 * 
 * 
 * @author Harper Jiang
 * 
 */
public class LogHelper {

	private static final MessageFormat ENTER = new MessageFormat(
			"Entering {0}.{1}");

	private static final MessageFormat EXIT = new MessageFormat(
			"Exiting {0}.{1}");
	private static final MessageFormat THROW = new MessageFormat(
			"Throwing from {0}.{1}");

	public static final void entering(Logger logger, String className,
			String methodName) {
		logger.debug(ENTER.format(new Object[] { className, methodName }));
	}

	public static final void throwing(Logger logger, String className,
			String methodName, Throwable throwable) {
		logger.debug(THROW.format(new Object[] { className, methodName }),
				throwable);
	}

	public static final void exiting(Logger logger, String className,
			String methodName) {
		logger.debug(EXIT.format(new Object[] { className, methodName }));
	}

	public static final void warnThrowing(Logger logger, String className,
			String methodName, Throwable throwable) {
		logger.warn(THROW.format(new Object[] { className, methodName }),
				throwable);
	}
	
	public static final void entering(Log logger, String className,
			String methodName) {
		logger.debug(ENTER.format(new Object[] { className, methodName }));
	}

	public static final void throwing(Log logger, String className,
			String methodName, Throwable throwable) {
		logger.debug(THROW.format(new Object[] { className, methodName }),
				throwable);
	}

	public static final void exiting(Log logger, String className,
			String methodName) {
		logger.debug(EXIT.format(new Object[] { className, methodName }));
	}

	public static final void warnThrowing(Log logger, String className,
			String methodName, Throwable throwable) {
		logger.warn(THROW.format(new Object[] { className, methodName }),
				throwable);
	}
}
