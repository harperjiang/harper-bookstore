package org.harper.frm.core.tools;

import java.util.List;

import org.apache.commons.lang.Validate;

public class ToolPack {

	public static boolean subClass(Class<?> parentClass, Class<?> subClass) {
		Validate.notNull(parentClass);
		Validate.notNull(subClass);
		for (Class<?> current = subClass; !Object.class.equals(current); current = current
				.getSuperclass())
			if (!parentClass.isInterface() && parentClass.equals(current))
				return true;
			else {
				Class<?>[] is = current.getInterfaces();
				for (int i = 0; i < is.length; i++)
					if (is[i] == parentClass)
						return true;
			}
		return false;
	}

	public final static boolean equal(Object a, Object b) {
		return (a == b) || (a != null && b != null && a.equals(b));
	}

	public final static boolean equalList(List a, List b) {
		if (a.size() != b.size())
			return false;
		for (int i = 0; i < a.size(); i++) {
			if (!a.get(i).equals(b.get(i)))
				return false;
		}
		return true;
	}

	public static final int hashCodeCal(Object[] objects) {
		int result = 7;
		for (int i = 0; i < objects.length; i++) {
			result = result
					* 37
					+ (objects[i] == null ? 0
							: objects[i] instanceof List ? hashCodeCal(((List) objects[i])
									.toArray())
									: objects[i].hashCode());
		}
		return result;
	}
}
