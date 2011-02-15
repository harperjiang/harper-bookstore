package org.harper.frm.core.tools.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Used together with <code>Bean2XMLSerializer</code>, this class targeting at
 * storing and reusing reflection related infomations in order to improve the
 * performance of xml serializer.
 * 
 * @author Harper Jiang
 * @since ips.frm.core 1.0
 * @version 1.0 Jun 17, 2009
 */
public class Bean2XMLCache {

	private Class<?> beanClass;

	private String[] namespaces;

	private Field textNode;

	private List<Object[]> attributes;

	private List<Field> subelems;

	public Bean2XMLCache() {
		super();
		attributes = new ArrayList<Object[]>();
		subelems = new ArrayList<Field>();
	}

	public Class<?> getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	public String[] getNamespaces() {
		return namespaces;
	}

	public void setNamespaces(String[] namespaces) {
		this.namespaces = namespaces;
	}

	public Field getTextNode() {
		return textNode;
	}

	public void setTextNode(Field textNode) {
		this.textNode = textNode;
	}

	public List<Object[]> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Object[]> attributes) {
		this.attributes = attributes;
	}

	public List<Field> getSubelems() {
		return subelems;
	}

	public void setSubelems(List<Field> subelems) {
		this.subelems = subelems;
	}

}
