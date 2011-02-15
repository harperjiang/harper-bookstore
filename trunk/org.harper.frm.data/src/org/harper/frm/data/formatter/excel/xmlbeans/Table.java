package org.harper.frm.data.formatter.excel.xmlbeans;

import java.util.List;

import org.harper.frm.core.tools.bean.Bean2XMLSerializer.Attribute;

public class Table {

	@Attribute(namespace="ss")
	public String ExpandedColumnCount;
	@Attribute(namespace="ss")
	public String ExpandedRowCount;
	@Attribute(namespace="x")
	public String FullColumn;
	@Attribute(namespace="x")
	public String FullRows;
	
	List<Column> Column;
	
	List<Row> Row;
}
