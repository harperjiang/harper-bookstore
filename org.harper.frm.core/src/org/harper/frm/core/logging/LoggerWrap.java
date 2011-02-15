package org.harper.frm.core.logging;

import org.apache.log4j.Logger;
import org.harper.frm.core.logging.ns.TLNS;
import org.harper.frm.core.logging.ns.TNS;


/**
 * <code>LoggerWrap</code>
 * 
 * @author Harper Jiang
 * @version 1.2
 * @since InvoicePodium 1.0
 * @see org.apache.commons.logging.Log
 * @see org.apache.log4j.Logger
 * @deprecated Since InvoicePodium 2.8. Use <code>LogManager</code> instead.
 */
public class LoggerWrap {

	/**
	 * Get global logger
	 * 
	 * @return <code>org.apache.commons.logging.Log</code> with the name
	 *         com.oocl.ivo.frm
	 */
	public static Logger getGLogger() {
		return Logger.getLogger(LogManager.resolve(TNS.NS_FRM));
	}

	/**
	 * Get logger for each module by module name
	 * 
	 * @return org.apache.log4j.Logger with the name com.oocl.ivo.frm.moduleName
	 */
	public static Logger getMLogger(String moduleName) {
		return Logger.getLogger(LogManager.resolve(TNS.NS_FRM + "." + moduleName));
	}

	public static Logger getDeveloperLog() {
		return getMLogger(LogManager.resolve(TNS.DEVELOP));
	}

	/**
	 * Get Logger for Toplink
	 * 
	 * @return org.apache.log4j.Logger with the name oracle.toplink
	 */
	public static Logger getTLogger() {
		return Logger.getLogger(LogManager.resolve(TLNS.NS_TOPLINK));
	}

	/**
	 * Get Logger for Toplink Session indicated by sessionName
	 * 
	 * @param sessionName
	 * @return org.apache.log4j.Logger with the name oracle.toplink.sessionName
	 */
	public static Logger getTSLogger(String sessionName) {
		return Logger.getLogger(LogManager.resolve(TLNS.NS_TOPLINK + "." + sessionName));
	}

	/**
	 * Get Logger for Toplink Session indicated by sessionName and category
	 * 
	 * @param sessionName
	 * @param category
	 * @return org.apache.log4j.Logger with the name
	 *         oracle.toplink.sessionName.category
	 */

	public static Logger getTSLogger(String sessionName, String category) {
		return Logger.getLogger(LogManager.resolve(TLNS.NS_TOPLINK + "." + sessionName + "."
				+ category));
	}

	public static Logger getLogger(Class<?> clazz) {
		return Logger.getLogger(LogManager.resolve(clazz.getName()));
	}
}
