package org.harper.frm.core.tools.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class JavaBeanAccess extends BeanAccess {

	private static final Object[] nullObj = new Object[] {};

	public void set(String accessStr, Object src, Object value) {
		try {
			Object[] os = translate(accessStr, src);
			if (os == null)
				return;
			String name = (String) os[1];
			Object bean = os[0];
			Object[] tpara = new Object[] { value };

			Object[] res;

			res = recFind(bean.getClass(), name, false);
			if (res != null) {
				Method method = (Method) res[1];
				if (Modifier.isPublic(method.getModifiers())) {
					method.setAccessible(true);
					method.invoke(bean, tpara);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

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
			Object[] tpara;
			tpara = nullObj;

			Object[] res = recFind(bean.getClass(), name, true);
			if (res == null)
				return null;

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
		Object[] curRP = new Object[] {};
		for (int i = 0; i < theStrs.length - 1; i++) {
			Object[] recRes;

			curRP = new Object[] {};
			String name = theStrs[i];
			recRes = recFind(curObj.getClass(), name, true);
			if (recRes == null)
				return null;
			else {
				curObj = ((Method) recRes[1]).invoke(curObj, curRP);
			}
		}
		return new Object[] { curObj, theStrs[theStrs.length - 1] };
	}

	private Object[] recFind(Class<?> clazz, String attrName, boolean read) {
		// String key = composeCacheStr(clazz, methodName);
		// if (methodCache.containsKey(key))
		// return new Object[] { clazz, methodCache.get(key) };
		try {
			BeanInfo info = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] props = info.getPropertyDescriptors();
			for (int i = 0; i < props.length; i++)
				if (props[i].getName().equals(attrName))
					return new Object[] {
							clazz,
							read ? props[i].getReadMethod() : props[i]
									.getWriteMethod() };

			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public String[] fields(Object bean) {
		return null;
	}
}
