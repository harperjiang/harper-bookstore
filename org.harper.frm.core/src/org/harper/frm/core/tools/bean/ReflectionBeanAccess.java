package org.harper.frm.core.tools.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Hashtable;
import java.util.Vector;

public class ReflectionBeanAccess extends BeanAccess {
	private static final Class[] nullClass = new Class[] {};

	private static final Object[] nullObj = new Object[] {};

	private Hashtable methodCache;

	ReflectionBeanAccess() {
		methodCache = new Hashtable();
	}

	/**
	 * Copy between two beans field by field. DEEP COPY
	 * 
	 * @since 1.1
	 * @param from
	 * @param to
	 */

	public void copy(Object from, Object to, boolean needClone) {
		try {
			if (from == null || to == null
			/* || !from.getClass().equals(to.getClass()) */)
				return;
			Vector v = recCollectFields(from.getClass());
			for (int i = 0; i < v.size(); i++) {
				Object value = get(v.get(i).toString(), from);
				Object clone = value;
				if (value != null && needClone) {
					Object[] methods = recFind(value.getClass(), "clone",
							nullClass);
					if (methods != null) {
						Method method = (Method) methods[1];
						if (Modifier.isPublic(method.getModifiers())) {
							method.setAccessible(true);
							clone = method.invoke(value, nullObj);
						}
					}
				}
				set(v.get(i).toString(), to, clone);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * Get all declared and inherited fields
	 * 
	 * @param bean
	 *            Class or Object to be extracted
	 * @return all declared fields contains in this class and all super classes.
	 * @since 1.1
	 */
	public String[] fields(Object bean) {
		Class clazz;
		if (bean instanceof Class)
			clazz = (Class) bean;
		else
			clazz = bean.getClass();
		Vector v = recCollectFields(clazz);
		String[] s = new String[v.size()];
		for (int i = 0; i < s.length; i++)
			s[i] = v.get(i).toString();
		return s;
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
		try {
			Object[] os = translate(accessStr, src);
			if (os == null)
				return;
			String name = (String) os[1];
			String fieldName = name;
			Object bean = os[0];
			Class[] para;
			Object[] tpara;
			try {
				int val = Integer.parseInt(name);
				name = "set";
				para = new Class[] { Integer.TYPE, Object.class };
				tpara = new Object[] { new Integer(val), value };
			} catch (Exception e) {
				name = accessName(name, "set");
				if (value == null)
					para = null;
				else
					para = new Class[] { value.getClass() };
				tpara = new Object[] { value };
			}
			Object[] res;
			if (para != null) {
				res = recFind(bean.getClass(), name, para);
				if (res != null) {
					Method method = (Method) res[1];
					if (Modifier.isPublic(method.getModifiers())) {
						method.setAccessible(true);
						method.invoke(bean, tpara);
					}
				} else {
					// Access directly to the field
					// Field field = bean.getClass().getField(fieldName);
					// field.set(bean, value);
				}
			} else {
				res = recFindName(bean.getClass(), name);
				if (res != null) {
					Method method = (Method) res[1];
					if (Modifier.isPublic(method.getModifiers())) {
						method.setAccessible(true);
						method.invoke(bean, new Object[] { null });
					}
				} else {
					// Access directly to the field
					// Field field = bean.getClass().getField(fieldName);
					// field.set(bean, null);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	/**
	 * @since 1.0
	 * @param accessStr
	 * @param src
	 * @return
	 */
	public Object get(String accessStr, Object src) {
		try {
			if (accessStr.equals(""))
				return src;
			Object[] os = translate(accessStr, src);
			if (os == null)
				return null;
			String name = (String) os[1];
			Object bean = os[0];
			if (bean == null)
				return null;
			Class beanClass = bean.getClass();
			Class[] para;
			Object[] tpara;
			try {
				int val = Integer.parseInt(name);
				name = "get";
				para = new Class[] { Integer.TYPE };
				tpara = new Object[] { new Integer(val) };
			} catch (Exception e) {
				para = nullClass;
				tpara = nullObj;
			}
			name = accessName(name, "get");
			Object[] res = recFind(bean.getClass(), name, para);
			if (res == null) {
				name = accessName(name, "is");
				res = recFind(bean.getClass(), name, para);
				if (res == null) {
					name = accessName(name, "");
					res = recFind(bean.getClass(), name, para);
					if (res == null)
						return null;
				}
			}
			Method method = (Method) res[1];
			if (Modifier.isPublic(method.getModifiers())) {
				method.setAccessible(true);
				return method.invoke(bean, tpara);
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Object[] translate(String accessStr, Object src)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		String[] theStrs = accessStr.split("\\.");

		Object curObj = src;
		Class[] curPara = new Class[] {};
		Object[] curRP = new Object[] {};
		for (int i = 0; i < theStrs.length - 1; i++) {
			Object[] recRes;
			try {
				curRP = new Object[] { Integer.valueOf(theStrs[i]) };
				recRes = recFind(curObj.getClass(), "get", curPara);
				curPara = new Class[] { Integer.TYPE };
			} catch (NumberFormatException e) {
				curRP = new Object[] {};
				String name = theStrs[i];
				name = accessName(name, "get");
				recRes = recFind(curObj.getClass(), name, curPara);
				if (recRes == null) {
					name = accessName(name, "is");
					recRes = recFind(curObj.getClass(), name, curPara);
					if (recRes == null) {
						name = accessName(name, "");
						recRes = recFind(curObj.getClass(), name, curPara);
						if (recRes == null)
							return null;
					}
				}
				curPara = new Class[] {};
			}

			if (recRes == null)
				return null;
			else {
				curObj = ((Method) recRes[1]).invoke(curObj, curRP);
			}
		}
		return new Object[] { curObj, theStrs[theStrs.length - 1] };
	}

	private Vector recCollectFields(Class clazz) {
		if (clazz.equals(Object.class))
			return new Vector();
		Vector v = recCollectFields(clazz.getSuperclass());
		Field[] fs = clazz.getDeclaredFields();
		for (int i = 0; i < fs.length; i++)
			v.add(fs[i].getName());
		return v;
	}

	// private StringBuffer stringBuffer;
	//
	// private StringBuffer getBlankBuffer() {
	// if (null == stringBuffer)
	// stringBuffer = new StringBuffer();
	// if (stringBuffer.length() > 0)
	// stringBuffer.delete(0, stringBuffer.length());
	// return stringBuffer;
	// }
	// //
	// private String composeCacheStr(Class clazz, String methodName) {
	// return getBlankBuffer().append(clazz.getName()).append("#").append(
	// methodName).toString();
	//	}

	private Object[] recFind(Class clazz, String methodName, Class[] para)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
//		String key = composeCacheStr(clazz, methodName);
//		if (methodCache.containsKey(key))
//			return new Object[] { clazz, methodCache.get(key) };
		try {
			Method m = clazz.getDeclaredMethod(methodName, para);
//			methodCache.put(key, m);
			return new Object[] { clazz, m };
		} catch (NoSuchMethodException e) {
			if (clazz.equals(Object.class))
				return null;
			return recFind(clazz.getSuperclass(), methodName, para);
		}
	}

	private Object[] recFindName(Class clazz, String methodName) {
		Method[] m = clazz.getDeclaredMethods();
		for (int i = 0; i < m.length; i++)
			if (m[i].getName().equals(methodName))
				return new Object[] { clazz, m[i] };
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
