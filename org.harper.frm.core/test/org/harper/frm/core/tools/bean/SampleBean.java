package org.harper.frm.core.tools.bean;

import org.harper.frm.core.tools.bean.Bean2XMLSerializer.Attribute;
import org.harper.frm.core.tools.bean.Bean2XMLSerializer.Namespace;

@Namespace(names = { "xmlns", "abcdef" })
public class SampleBean {

	@Attribute(namespace = "ss")
	public String AttrA;

	@Attribute()
	public String AttrB;

	public String SubElement;

}
