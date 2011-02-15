package org.harper.frm.core.tools.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

/**
 * Searialize a bean into XML
 * 
 * @author Harper Jiang
 * @since ips.frm.core 1.0
 * @version 1.0 Jun 15, 2009
 */
public class Bean2XMLSerializer {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface Namespace {
		String[] names() default {};
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface Attribute {
		String namespace() default "";
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface TextNode {
	}

	private static ConcurrentMap<Class<?>, Bean2XMLCache> caches = new ConcurrentHashMap<Class<?>, Bean2XMLCache>();

	protected synchronized static Bean2XMLCache getCache(Class<?> beanClass) {
		if (caches.containsKey(beanClass)) {
			return caches.get(beanClass);
		}
		Bean2XMLCache cache = new Bean2XMLCache();
		cache.setBeanClass(beanClass);

		Namespace ns = beanClass.getAnnotation(Namespace.class);

		// Namespace
		if (ns != null)
			cache.setNamespaces(ns.names());

		// Elements;

		Field[] fields = beanClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			int mod = fields[i].getModifiers();
			if (Modifier.isPrivate(mod) || Modifier.isFinal(mod)
					|| Modifier.isProtected(mod) || Modifier.isStatic(mod))
				continue;
			Attribute attr = fields[i].getAnnotation(Attribute.class);
			TextNode tn = fields[i].getAnnotation(TextNode.class);
			if (null != attr) {
				cache.getAttributes().add(
						new Object[] {
								fields[i],
								StringUtils.isEmpty(attr.namespace()) ? null
										: attr.namespace() });
			} else if (tn != null)
				cache.setTextNode(fields[i]);
			else
				cache.getSubelems().add(fields[i]);
		}

		caches.put(beanClass, cache);
		return cache;
	}

	/**
	 * 
	 * @param output
	 * @param bean
	 * @throws IllegalArgumentException
	 * @throws IOException
	 * @throws IllegalAccessException
	 */
	public static void serialize(OutputStream output, Object bean)
			throws IllegalArgumentException, IOException,
			IllegalAccessException {
		serialize(output, bean, bean.getClass().getSimpleName());
	}

	/**
	 * 
	 * @param os
	 * @param bean
	 * @param elementName
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static void serialize(OutputStream os, Object bean,
			String elementName) throws IOException, IllegalArgumentException,
			IllegalAccessException {
		// Return if null
		if (null == bean)
			return;
		// If a simple String
		if (bean instanceof String) {
			os.write(MessageFormat.format(SIMPLE_ELEM_PATTERN, elementName,
					bean).getBytes());
			return;
		}
		// If a Collection
		if (bean instanceof Collection) {
			Collection<?> col = (Collection<?>) bean;
			for (Object obj : col)
				serialize(os, obj, elementName);
			return;
		}

		// Start
		beginTag(os, bean, elementName);
		// Print Attributes,
		Bean2XMLCache cache = getCache(bean.getClass());
		PrintWriter pw = new PrintWriter(os);
		// Print subelements.
		for (Field subele : cache.getSubelems())
			serialize(os, subele.get(bean), subele.getName());
		// Print Text Node
		if (null != cache.getTextNode())
			pw.print(cache.getTextNode().get(bean));
		pw.flush();
		// Stop
		endTag(os, bean, elementName);
	}

	public static void beginTag(OutputStream output, Object bean)
			throws IllegalArgumentException, IllegalAccessException {
		Validate.notNull(bean);
		beginTag(output, bean, bean.getClass().getSimpleName());
	}

	public static void beginTag(OutputStream os, Object bean, String elementName)
			throws IllegalArgumentException, IllegalAccessException {
		Bean2XMLCache cache = getCache(bean.getClass());
		PrintWriter pw = new PrintWriter(os);

		pw.print(MessageFormat.format(ELEMENT_START, elementName));
		// Start
		if (null != cache.getNamespaces()) {
			String[] nss = cache.getNamespaces();
			for (int i = 0; i < nss.length; i += 2) {
				pw.print(MessageFormat.format(ATTR_PATTERN, "", nss[i],
						nss[i + 1]));
			}
		}

		// Print Attributes,

		for (Object[] attr : cache.getAttributes()) {
			// Print this attribute
			Field field = (Field) attr[0];
			String ns = (String) attr[1];
			if (!StringUtils.isEmpty((String) field.get(bean)))
				pw.print(MessageFormat.format(ATTR_PATTERN, null == ns ? ""
						: ns + ":", field.getName(), field.get(bean)));

		}

		pw.print(ELEMENT_START_CLOSE);
		pw.flush();
	}

	public static void endTag(OutputStream output, Object bean)
			throws IllegalArgumentException, IllegalAccessException {
		Validate.notNull(bean);
		endTag(output, bean, bean.getClass().getSimpleName());
	}

	public static void endTag(OutputStream os, Object bean, String elementName) {
		PrintWriter pw = new PrintWriter(os);
		pw.print(MessageFormat.format(ELEMENT_STOP, elementName));
		pw.flush();
	}

	protected static final String SIMPLE_ELEM_PATTERN = "<{0}>{1}</{0}>";

	protected static final String ELEMENT_START = "<{0}";

	protected static final String ELEMENT_START_CLOSE = ">";

	protected static final String ATTR_PATTERN = " {0}{1}=\"{2}\"";

	protected static final String ELEMENT_STOP = "</{0}>";
}
