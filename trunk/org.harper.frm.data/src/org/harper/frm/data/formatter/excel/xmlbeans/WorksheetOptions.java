package org.harper.frm.data.formatter.excel.xmlbeans;

import org.harper.frm.core.tools.bean.Bean2XMLSerializer.Namespace;

@Namespace(names = { "xmlns", ExcelNS.EXCEL })
public class WorksheetOptions {

	public Print Print;

	public Panes Panes;

	public String ProtectObjects;

	public String ProtectScenarios;
}
