package org.harper.frm.data.formatter.excel.xmlbeans;

import org.harper.frm.core.tools.bean.Bean2XMLSerializer.Attribute;

public class Worksheet {

	@Attribute(namespace="ss")
	public String Name;
	
	public Table Table;
	
	public WorksheetOptions WorksheetOptions;
}
