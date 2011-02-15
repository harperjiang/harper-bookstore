package org.harper.frm.core.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Vector;

import org.apache.commons.lang.Validate;

/**
 * 
 * @author Harper Jiang
 * @since Core 1.0
 * @version 1.0 Jul 9, 2009
 */
public class ReflectionHelper {

	/**
	 * 
	 * @param beanClass
	 * @param annotationClass
	 * @return
	 */
	public static Field[] collectFieldWithAnnotation(Class<?> beanClass,
			final Class<? extends Annotation> annotationClass) {
		Validate.isTrue(annotationClass.isAnnotation());
		final Vector<Field> annoFields = new Vector<Field>();
		browse(beanClass, new ClassHierarchyBrowseCallback() {
			public void callback(Class<?> clazz) {
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					if (null != field.getAnnotation(annotationClass))
						annoFields.add(field);
				}
			}
		});
		Field[] retval = new Field[annoFields.size()];
		annoFields.toArray(retval);
		return retval;
	}

	/**
	 * 
	 * @param start
	 * @param callback
	 */
	public static void browse(Class<?> start,
			ClassHierarchyBrowseCallback callback) {
		Validate.notNull(start);
		Validate.notNull(callback);
		Class<?> current = start;
		while (current != Object.class) {
			callback.callback(current);
			current = current.getSuperclass();
		}
	}

	public static interface ClassHierarchyBrowseCallback {
		public void callback(Class<?> clazz);
	}
}
