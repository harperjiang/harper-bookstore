package org.harper.frm.data.formatter.excel.xmlbeans;

import org.harper.frm.core.tools.bean.Bean2XMLSerializer.Attribute;

public class Style {

	@Attribute(namespace = "ss")
	public String ID;
	@Attribute(namespace = "ss")
	public String Name;
	public Alignment Alignement;
	public String Borders;
	public Font Font;
	public Interior Interior;
	public NumberFormat NumberFormat;
	public String Protection;
}
