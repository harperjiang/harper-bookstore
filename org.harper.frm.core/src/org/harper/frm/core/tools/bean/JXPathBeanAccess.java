package org.harper.frm.core.tools.bean;

import org.apache.commons.jxpath.JXPathContext;

public class JXPathBeanAccess {

	public static Object get(Object source, String key) {
		if (key == null || key.length() == 0)
			return source;
		JXPathContext context = JXPathContext.newContext(source);
		return context.getValue(key);
	}

	public static void set(Object source, String path, Object value) {
		JXPathContext context = JXPathContext.newContext(source);
		context.setValue(path, value);
	}
}
