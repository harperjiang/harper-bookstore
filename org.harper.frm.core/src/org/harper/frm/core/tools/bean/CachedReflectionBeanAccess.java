package org.harper.frm.core.tools.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class CachedReflectionBeanAccess extends BeanAccess {
	private static final Class<?>[] nullClass = new Class[] {};

	private static final Object[] nullObj = new Object[] {};

	private Map<String,Object[]> getterCache;

	private Map<String,Object[]> setterCache;

	CachedReflectionBeanAccess() {
		getterCache = Collections.synchronizedMap(new HashMap<String,Object[]>());
		setterCache = Collections.synchronizedMap(new HashMap<String,Object[]>());
	}

	/**
	 * Set the bean field indicated by accessStr via "set" Method.
	 * 
	 * @since 1.0
	 * @param accessStr
	 * @param src
	 * @param value
	 */
	public void set(String accessStr, Object src, Object value) {
		if (null == src || StringUtils.isEmpty(accessStr))
			throw new NullPointerException();
		try {
			String[] accessStrs = accessStr.split("\\.");
			if (accessStrs.length > 1) {
				set(accessStr.substring(accessStr.indexOf('.') + 1), get(
						accessStrs[0], src), value);
				return;
			}
			Object[] result = getSetter(src.getClass(), accessStr, value);
			if (result == null)
				throw new NoSuchMethodException(src.getClass() + ":"
						+ accessStr);
			Method method = (Method) result[0];
			if (Modifier.isPublic(method.getModifiers())) {
				method.setAccessible(true);
				method.invoke(src, (Object[]) result[1]);
				return;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @since 1.0
	 * @param accessStr
	 * @param src
	 * @return
	 */
	public Object get(String accessStr, Object src) {
		if (null == src)
			throw new NullPointerException();
		try {
			if (accessStr.equals(""))
				return src;
			String[] accessStrs = accessStr.split("\\.");
			if (accessStrs.length > 1) {
				return get(accessStr.substring(accessStr.indexOf('.') + 1),
						get(accessStrs[0], src));
			}
			Object[] result = getGetter(src.getClass(), accessStr);
			if (result == null)
				throw new NoSuchMethodException(src.getClass() + ":"
						+ accessStr);
			Method method = (Method) result[0];
			if (Modifier.isPublic(method.getModifiers())) {
				method.setAccessible(true);
				return method.invoke(src, (Object[]) result[1]);
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Object[] getGetter(Class<?> clazz, String attribute) {
		try {
			String key = composeCacheStr(clazz, attribute);
			if (getterCache.containsKey(key))
				return (Object[]) getterCache.get(key);
			String name = attribute;

			Class<?>[] para;
			Object[] tpara;
			try {
				int val = Integer.parseInt(attribute);
				name = "get";
				para = new Class[] { Integer.TYPE };
				tpara = new Object[] { new Integer(val) };
			} catch (Exception e) {
				name = accessName(name, "get");
				para = nullClass;
				tpara = nullObj;
			}

			Method method = recFind(clazz, name, para);

			if (method == null) {
				name = accessName(name, "is");
				method = recFind(clazz, name, para);
				if (method == null)
					throw new NoSuchMethodException();
			}
			Object[] result = new Object[] { method, tpara };
			getterCache.put(key, result);
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	private Object[] getSetter(Class<?> clazz, String attribute, Object value) {
		String key = composeCacheStr(clazz, attribute);
		if (setterCache.containsKey(key)) {
			return (Object[]) setterCache.get(key);
		}
		try {
			Class<?>[] para;
			Object[] tpara;
			String name;
			try {
				int val = Integer.parseInt(attribute);
				name = "set";
				para = new Class[] { Integer.TYPE, Object.class };
				tpara = new Object[] { new Integer(val), value };
			} catch (Exception e) {
				name = accessName(attribute, "set");
				if (value == null)
					para = null;
				else
					para = new Class[] { value.getClass() };
				tpara = new Object[] { value };
			}
			Object[] res = null;

			Method method = recFind(clazz, name, para);
			if (method != null) {
				res = new Object[] { method, tpara };
			}
			return res;
		} catch (Exception e) {
			return null;
		}
	}

	private StringBuffer getBlankBuffer() {
		return new StringBuffer();
	}

	//
	private String composeCacheStr(Class<?> clazz, String methodName) {
		return getBlankBuffer().append(clazz.getName()).append("#").append(
				methodName).toString();
	}

	private Method recFind(Class<?> clazz, String methodName, Class<?>[] para)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		if (para == null)
			return recFindName(clazz, methodName);
		try {
			Method m = clazz.getDeclaredMethod(methodName, para);
			return m;
		} catch (NoSuchMethodException e) {
			if (clazz.equals(Object.class))
				return null;
			return recFind(clazz.getSuperclass(), methodName, para);
		}
	}

	private Method recFindName(Class<?> clazz, String methodName) {
		Method[] m = clazz.getDeclaredMethods();
		for (int i = 0; i < m.length; i++)
			if (m[i].getName().equals(methodName))
				return m[i];
		if (clazz.equals(Object.class))
			return null;
		return recFindName(clazz.getSuperclass(), methodName);
	}

	private static String accessName(String origin, String prefix) {
		StringBuffer sb = new StringBuffer();
		sb.append(prefix);
		sb.append(Character.toUpperCase(origin.charAt(0)));
		sb.append(origin.substring(1));
		return sb.toString();
	}
}
