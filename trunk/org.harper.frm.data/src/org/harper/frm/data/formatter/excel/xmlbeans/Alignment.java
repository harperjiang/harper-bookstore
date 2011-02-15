package org.harper.frm.data.formatter.excel.xmlbeans;

import org.harper.frm.core.tools.bean.Bean2XMLSerializer.Attribute;

public class Alignment {

	@Attribute(namespace = "ss")
	public String Vertical;
	@Attribute(namespace = "ss")
	public String Horizontal;
}
