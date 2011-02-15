package org.harper.frm.data.formatter.excel.xmlbeans;

import org.harper.frm.core.tools.bean.Bean2XMLSerializer.Attribute;

public class Interior {
	@Attribute(namespace = "ss")
	public String Color;
	@Attribute(namespace = "ss")
	public String Pattern = "Solid";
}
