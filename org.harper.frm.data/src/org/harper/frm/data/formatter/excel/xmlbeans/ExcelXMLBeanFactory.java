package org.harper.frm.data.formatter.excel.xmlbeans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExcelXMLBeanFactory {

	public static Workbook createWorkbook() {
		Workbook book = new Workbook();

		return book;
	}

	public static DocumentProperties createDocumentProperties() {
		DocumentProperties docProp = new DocumentProperties();
		docProp.Author = "Harper Jiang";
		docProp.LastAuthor = "Harper Jiang";
		docProp.Company = "OOCL";
		docProp.Version = "11.8132";
		return docProp;
	}

	public static ExcelWorkbook createExcelWorkbook() {
		ExcelWorkbook ewb = new ExcelWorkbook();

		ewb.WindowHeight = "10000";
		ewb.WindowWidth = "10000";

		return ewb;
	}

	public static Style createDefaultStyle() {
		Style style = new Style();
		style.ID = "Default";
		style.Name = "Normal";
		style.Borders = "";
		style.Interior = new Interior();
		style.Protection = "";
		style.Font = new Font();
		style.Alignement = new Alignment();
		style.NumberFormat = new NumberFormat();

		return style;
	}
	
	public static Style createSimpleStyle() {
		Style style = new Style();
		style.Borders = "";
		style.Interior = new Interior();
		style.Protection = "";
		style.Font = new Font();
		style.Alignement = new Alignment();
		style.NumberFormat = new NumberFormat();

		return style;
	}

	public static WorksheetOptions createWorksheetOptions() {
		WorksheetOptions wo = new WorksheetOptions();

		return wo;
	}

	public static Worksheet createWorksheet() {
		Worksheet ws = new Worksheet();

		return ws;
	}

	public static Table createTable() {
		Table table = new Table();

		return table;
	}

	public static Data createData(Object input) {
		Data data = new Data();

		if (input instanceof Number) {
			data.Type = TYPE_NUMBER;
			data.Value = String.valueOf(input);
		} else if (input instanceof Date) {
			data.Type = TYPE_DATETIME;
			StringBuilder builder = new StringBuilder();
			Date date = (Date)input;
			builder.append(new SimpleDateFormat(DATE_FORMAT).format(date));
			builder.append("T");
			builder.append(new SimpleDateFormat(TIME_FORMAT).format(date));
			data.Value = builder.toString();
		} else {
			data.Type = TYPE_STRING;
			data.Value = input == null ? "" : String.valueOf(input);
		}
		return data;
	}

	public static String TYPE_STRING = "String";

	public static String TYPE_NUMBER = "Number";

	public static String TYPE_DATETIME = "DateTime";

	protected static final String DATE_FORMAT = "yyyy-MM-dd";
	protected static final String TIME_FORMAT = "HH:mm:ss.SSS";
}
