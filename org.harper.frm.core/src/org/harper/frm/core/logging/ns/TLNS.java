package org.harper.frm.core.logging.ns;
 
/**
 * The class name <code>TLNS</code> stands for "TopLink NameSpace". This class
 * contains toplink specified log namespaces.
 * 
 * @author Harper Jiang
 * @version 1.0
 */

public class TLNS {
	public static final String NS_TOPLINK = "oracle.toplink";

	public static final String NS_DEFAULTSESSION = "defaultsessionname";

	// The following are standards categories in TopLink Log Namespaces
	public static final String SQL = "sql";

	public static final String TRANSACTION = "transaction";

	public static final String EVENT = "event";

	public static final String CONNECTION = "connection";

	public static final String QUERY = "query";

	public static final String CACHE = "cache";

	public static final String PROPAGATION = "propagation";

	public static final String SEQUENCING = "sequencing";

	public static final String EJB = "ejb";

	// This is self-created category for profile info
	public static final String PROFILER = "profiler";

}
