package org.harper.frm.core.mbean;

/**
 * MBeans to manage Log configuration
 * 
 * <p>
 * The <tt>ObjectName</tt> for uniquely identifying the MXBean for the thread
 * system within an MBeanServer is: <blockquote> {@link #LOG_MBEAN_NAME
 * <tt>com.oocl.frm:type=Logging</tt>}
 * 
 * @author Harper
 * @since Core 1.0
 * @version 1.0 Jul 14, 2009
 */
public interface LogControlMBean {

	public static final String LOG_MBEAN_NAME = "com.oocl.frm:type=Logging";

	public void setLogLevel(String loggerName, String level);

	public String getLogLevel(String loggerName);
}
