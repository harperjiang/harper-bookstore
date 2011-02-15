package org.harper.frm.core;

import org.apache.commons.lang.StringUtils;

public class Oc4j1013PlatformInfo implements PlatformInfo {

	protected static final String JVM_KEY = "oracle.ons.indexid";

	protected static final String INST_KEY = "oracle.ons.instancename";

	private transient int jvmNo = -1;

	public synchronized int currentJvmNo() {
		if (jvmNo != -1)
			return jvmNo;
		String jvmKey = System.getProperty(JVM_KEY);
		if (StringUtils.isEmpty(jvmKey))
			jvmNo = 1;
		else
			try {
				jvmNo = Integer.parseInt(jvmKey.substring(jvmKey.length() - 1));
			} catch (NumberFormatException e) {
				jvmNo = 1;
			}
		return jvmNo;
	}

	public boolean inCluster() {
		return !StringUtils.isEmpty(System.getProperty(INST_KEY));
	}

}
