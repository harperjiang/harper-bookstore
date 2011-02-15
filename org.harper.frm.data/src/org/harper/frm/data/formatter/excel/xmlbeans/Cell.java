package org.harper.frm.data.formatter.excel.xmlbeans;

import org.harper.frm.core.tools.bean.Bean2XMLSerializer.Attribute;

public class Cell {

	@Attribute(namespace="ss")
	public String StyleID;
	
	public Data Data;
}
