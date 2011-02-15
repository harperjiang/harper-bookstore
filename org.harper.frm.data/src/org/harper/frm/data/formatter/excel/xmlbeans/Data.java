package org.harper.frm.data.formatter.excel.xmlbeans;

import org.harper.frm.core.tools.bean.Bean2XMLSerializer.Attribute;
import org.harper.frm.core.tools.bean.Bean2XMLSerializer.TextNode;

public class Data {

	@Attribute(namespace = "ss")
	public String Type;
	@TextNode
	public String Value;

}
