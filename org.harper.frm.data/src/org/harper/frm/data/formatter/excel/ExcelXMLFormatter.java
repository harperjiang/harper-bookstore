package org.harper.frm.data.formatter.excel;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.harper.frm.core.tools.bean.Bean2XMLSerializer;
import org.harper.frm.data.Extension;
import org.harper.frm.data.formatter.DefaultFileDescriber;
import org.harper.frm.data.formatter.DefaultFormatter;
import org.harper.frm.data.formatter.ITableContentProvider;
import org.harper.frm.data.formatter.excel.xmlbeans.Cell;
import org.harper.frm.data.formatter.excel.xmlbeans.ExcelXMLBeanFactory;
import org.harper.frm.data.formatter.excel.xmlbeans.Font;
import org.harper.frm.data.formatter.excel.xmlbeans.Row;
import org.harper.frm.data.formatter.excel.xmlbeans.Style;
import org.harper.frm.data.formatter.excel.xmlbeans.Styles;
import org.harper.frm.data.formatter.excel.xmlbeans.Table;
import org.harper.frm.data.formatter.excel.xmlbeans.Workbook;
import org.harper.frm.data.formatter.excel.xmlbeans.Worksheet;
import org.harper.frm.data.formatter.supp.FontBean;

/**
 * 
 * @author Harper Jiang
 * @since ips.frm.component 1.0
 * @version 1.0 Jun 15, 2009
 */
public class ExcelXMLFormatter extends DefaultFormatter {

	/**
	 * 
	 * @param fileName
	 * @param sheetPrefix
	 */
	public ExcelXMLFormatter(String fileName, String sheetPrefix) {
		this(fileName, new String[] { sheetPrefix });
	}

	/**
	 * 
	 * @param fileName
	 * @param sheetPrefix
	 */
	public ExcelXMLFormatter(String fileName, String[] sheetPrefix) {
		super();
		setPageSize(XLSFormatter.MAX_ROW);
		setDescriber(new DefaultFileDescriber(fileName, Extension.xls, null));
		this.sheetPrefix = sheetPrefix;
	}

	/**
	 * 
	 * @param fileName
	 * @param sheetPrefix
	 * @param namePattern
	 */
	public ExcelXMLFormatter(String fileName, String sheetPrefix,
			String namePattern) {
		this(fileName, new String[] { sheetPrefix }, namePattern);
	}

	/**
	 * 
	 * @param fileName
	 * @param sheetPrefix
	 * @param namePattern
	 */
	public ExcelXMLFormatter(String fileName, String[] sheetPrefix,
			String namePattern) {
		super();
		setPageSize(XLSFormatter.MAX_ROW);
		setDescriber(new DefaultFileDescriber(fileName, Extension.xls,
				namePattern));
		this.sheetPrefix = sheetPrefix;
	}

	private boolean usecolor;

	public boolean isUsecolor() {
		return usecolor;
	}

	public void setUsecolor(boolean usecolor) {
		this.usecolor = usecolor;
	}

	private String[] sheetPrefix;

	public String[] getSheetPrefix() {
		return sheetPrefix;
	}

	protected static final String XML_HEADER = "<?xml version=\"1.0\"?>";

	protected static final String EXCEL_HEADER = "<?mso-application progid=\"Excel.Sheet\"?>";

	transient String[] columnNames;

	@Override
	protected void createFile() throws IOException {
		Validate.notNull(getFileOutputStream());

		getFileOutputStream().write(XML_HEADER.getBytes());
		getFileOutputStream().write(EXCEL_HEADER.getBytes());

		Workbook workbook = ExcelXMLBeanFactory.createWorkbook();

		try {
			Bean2XMLSerializer.beginTag(getFileOutputStream(), workbook);
			Bean2XMLSerializer.serialize(getFileOutputStream(),
					ExcelXMLBeanFactory.createDocumentProperties());
			Bean2XMLSerializer.serialize(getFileOutputStream(),
					ExcelXMLBeanFactory.createExcelWorkbook());

			// TODO Styles
			FontBean font = getFont();

			Styles styles = new Styles();
			Style oddStyle = ExcelXMLBeanFactory.createSimpleStyle();
			Style evenStyle = ExcelXMLBeanFactory.createSimpleStyle();
			Style titleStyle = ExcelXMLBeanFactory.createSimpleStyle();

			if (font != null) {
				fillStyle(oddStyle, font);
				fillStyle(evenStyle, font);
				fillStyle(titleStyle, font);
			}
			if (usecolor)
				oddStyle.Interior.Color = "#FFFF99";
			oddStyle.ID = ODD_ROW;
			styles.Style.add(oddStyle);
			if (usecolor)
				evenStyle.Interior.Color = "#CCFFCC";
			evenStyle.ID = EVEN_ROW;
			styles.Style.add(evenStyle);
			titleStyle.ID = TITLE_STYLE;
			titleStyle.Font.Bold = "1";
			titleStyle.Interior.Color = "#C0C0C0";
			styles.Style.add(titleStyle);

			Bean2XMLSerializer.serialize(getFileOutputStream(), styles);

		} catch (IllegalArgumentException e) {
			// TODO Log
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// TODO Log
			throw new RuntimeException(e);
		}

	}

	@Override
	public void newSection(String[] header) throws IOException {
		// Create Column Descriptor
		columnNames = new String[header.length];
		System.arraycopy(header, 0, columnNames, 0, header.length);
	}

	@Override
	protected void finalizeFile() throws IOException {
		try {
			// If Current Sheet not null Then
			// Table End Tag
			// WorksheetOptions Serialize
			// Worksheet End Tag
			// End If
			// Workbook End Tag
			if (!oldSheetClosed) {
				Bean2XMLSerializer.endTag(getFileOutputStream(), new Table());
				Bean2XMLSerializer.serialize(getFileOutputStream(),
						ExcelXMLBeanFactory.createWorksheetOptions());
				Bean2XMLSerializer.endTag(getFileOutputStream(),
						new Worksheet());
			}
			Workbook workbook = new Workbook();
			Bean2XMLSerializer.endTag(getFileOutputStream(), workbook);
		} catch (IllegalArgumentException e) {
			// TODO Log
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// TODO Log
			throw new RuntimeException(e);
		}
	}

	private boolean oldSheetClosed = true;

	protected static final String TITLE_STYLE = "titleStyle";

	protected static final String EVEN_ROW = "evenRowStyle";

	protected static final String ODD_ROW = "oddRowStyle";

	@Override
	protected void newPage() throws IOException {
		// If Current Sheet not null Then
		// Table End Tag
		// WorksheetOptions Serialize
		// Worksheet End Tag
		// End If;
		// Worksheet Begin Tag
		// Table Begin Tag
		// TODO Table Column Width
		// Title Row Serialize
		try {
			if (!oldSheetClosed) {
				Bean2XMLSerializer.endTag(getFileOutputStream(), new Table());
				Bean2XMLSerializer.serialize(getFileOutputStream(),
						ExcelXMLBeanFactory.createWorksheetOptions());
				Bean2XMLSerializer.endTag(getFileOutputStream(),
						new Worksheet());
			}
			Worksheet newsheet = ExcelXMLBeanFactory.createWorksheet();
			newsheet.Name = sheetPrefix[currentSection]
					+ (currentPage == 0 ? "" : String.valueOf(currentPage));

			Table table = ExcelXMLBeanFactory.createTable();
			Bean2XMLSerializer.beginTag(getFileOutputStream(), newsheet);
			Bean2XMLSerializer.beginTag(getFileOutputStream(), table);

			// Create title Row
			Row row = new Row();
			row.Cell = new ArrayList<Cell>();

			for (int i = 0; i < columnNames.length; i++) {
				Cell cell = new Cell();
				row.Cell.add(cell);
				cell.StyleID = TITLE_STYLE;
				cell.Data = ExcelXMLBeanFactory.createData(columnNames[i]);
			}
			Bean2XMLSerializer.serialize(getFileOutputStream(), row);

		} catch (IllegalArgumentException e) {
			// TODO Log
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// TODO Log
			throw new RuntimeException(e);
		}
		oldSheetClosed = false;
	}

	@Override
	protected void newRow(Object input, ITableContentProvider<?> provider)
			throws IOException {
		try {
			// Create Row
			// Serialize Row
			Row row = new Row();
			row.Cell = new ArrayList<Cell>();

			for (int i = 0; i < columnNames.length; i++) {
				Cell cell = new Cell();
				row.Cell.add(cell);
				Object cellElement = provider.get(input, i);
				cell.Data = ExcelXMLBeanFactory.createData(cellElement);
				cell.StyleID = currentRow % 2 == 0 ? EVEN_ROW : ODD_ROW;
			}
			Bean2XMLSerializer.serialize(getFileOutputStream(), row);
		} catch (IllegalArgumentException e) {
			// TODO Log
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// TODO Log
			throw new RuntimeException(e);
		}
	}

	protected static void fillStyle(Style style, FontBean font) {
		if (font != null) {
			if (style.Font == null)
				style.Font = new Font();
			if (!StringUtils.isEmpty(font.fontName)) {
				style.Font.FontName = font.fontName;
				style.Font.Family = "Modern";
			}
			style.Font.Size = String.valueOf(font.size);
		}
	}

}
