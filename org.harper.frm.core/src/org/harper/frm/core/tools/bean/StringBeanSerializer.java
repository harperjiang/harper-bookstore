package org.harper.frm.core.tools.bean;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 * Store all bean attributes as plain string, bypass the possible problem of JDK
 * version caused by object serialization
 * 
 * @author Harper Jiang
 * 
 */
public class StringBeanSerializer {

	public static final String SPLIT = ";";

	public static final String EQUAL = "=";

	protected static final String CLASS = "class";

	public static String serialize(Object obj) {
		if(obj == null)
			return null;
		try {
			Map props = BeanUtils.describe(obj);
			StringBuffer sb = new StringBuffer();
			sb.append(obj.getClass().getName()).append(SPLIT);
			Iterator its = props.entrySet().iterator();
			while (its.hasNext()) {
				Entry entry = (Entry) its.next();
				if (!CLASS.equals(entry.getKey())) {
					sb.append(entry.getKey()).append(EQUAL);
					if (entry.getValue() != null)
						sb.append(entry.getValue());
					sb.append(SPLIT);
				}
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Object deserialize(String str) {
		if (str == null || str.length() == 0)
			return null;
		try {
			String[] splits = str.split(SPLIT);
			Object obj = Thread.currentThread().getContextClassLoader()
					.loadClass(splits[0]).newInstance();
			for (int i = 1; i < splits.length; i++) {
				String[] pair = splits[i].split(EQUAL);
				if(2 == pair.length) {
					JXPathBeanAccess.set(obj, pair[0], pair[1]);
				}
			}
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
