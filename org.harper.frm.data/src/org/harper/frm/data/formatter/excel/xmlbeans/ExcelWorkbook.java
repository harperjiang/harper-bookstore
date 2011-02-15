package org.harper.frm.data.formatter.excel.xmlbeans;

import org.harper.frm.core.tools.bean.Bean2XMLSerializer.Namespace;

@Namespace(names = { "xmlns", ExcelNS.EXCEL })
public class ExcelWorkbook {

	public String WindowHeight;
	public String WindowWidth;
	public String WindowTopX = "0";
	public String WindowTopY = "0";
	public String ActiveSheet = "0";
	public String ProtectedStructure = "False";
	public String ProtectedWindows = "False";
}
