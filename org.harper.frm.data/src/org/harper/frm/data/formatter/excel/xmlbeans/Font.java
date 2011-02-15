package org.harper.frm.data.formatter.excel.xmlbeans;

import org.harper.frm.core.tools.bean.Bean2XMLSerializer.Attribute;

public class Font {

	@Attribute(namespace="x")
	public String Family = "Swiss";
	@Attribute(namespace="ss")
	public String FontName;
	@Attribute(namespace="ss")
	public String Bold;
	@Attribute(namespace="ss")
	public String Color;
	@Attribute(namespace="ss")
	public String Size;
}
