package org.harper.frm.data.formatter.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.harper.frm.data.Extension;
import org.harper.frm.data.formatter.DefaultFileDescriber;
import org.harper.frm.data.formatter.DefaultFormatter;
import org.harper.frm.data.formatter.ITableContentProvider;


/**
 * Convert table to Microsoft Excel XLS file based on
 * <code>DefaultFormatter</code>
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since frm.component 1.0
 */
public class XLSFormatter2 extends DefaultFormatter {

	private final String[] sheetPrefix;

	public String[] getSheetPrefix() {
		return sheetPrefix;
	}

	private boolean usecolor;

	public boolean isUsecolor() {
		return usecolor;
	}

	public void setUsecolor(boolean usecolor) {
		this.usecolor = usecolor;
	}

	public XLSFormatter2(String fileName, String[] sheetPrefix) {
		super();
		setPageSize(XLSFormatter.MAX_ROW);
		setDescriber(new DefaultFileDescriber(fileName, Extension.xls, null));
		this.sheetPrefix = sheetPrefix;
	}

	public XLSFormatter2(String fileName, String[] sheetPrefix,
			String namePattern) {
		super();
		setPageSize(XLSFormatter.MAX_ROW);
		setDescriber(new DefaultFileDescriber(fileName, Extension.xls,
				namePattern));
		this.sheetPrefix = sheetPrefix;
	}

	public XLSFormatter2(String fileName, String sheetPrefix) {
		super();
		setPageSize(XLSFormatter.MAX_ROW);
		setDescriber(new DefaultFileDescriber(fileName, Extension.xls, null));
		this.sheetPrefix = new String[] { sheetPrefix };
	}

	public XLSFormatter2(String fileName, String sheetPrefix, String namePattern) {
		super();
		setPageSize(XLSFormatter.MAX_ROW);
		setDescriber(new DefaultFileDescriber(fileName, Extension.xls,
				namePattern));
		this.sheetPrefix = new String[] { sheetPrefix };
	}

	transient HSSFWorkbook workbook;

	transient HSSFSheet currentSheet;

	transient List<ColumnDescriber> columnInfo;

	@Override
	protected void createFile() {
		// Create Excel Workbook
		workbook = new HSSFWorkbook();
	}

	@Override
	protected void newSection(String[] columnTitles) throws IOException {
		columnInfo = columninfo(columnTitles);
	}

	@Override
	protected void newRow(Object input, ITableContentProvider<?> cp) {
		HSSFRow row = currentSheet.createRow(currentRow + 1);

		for (int index = 0; index < columnInfo.size(); index++) {
			Object value = cp.get(input, index);
			ColumnDescriber descriptor = columnInfo.get(index);
			HSSFCell cell = row.createCell(index);
			descriptor.describe(workbook, cell, value);
		}
	}

	@Override
	public void newPage() {
		// Create Excel Sheet
		currentSheet = workbook.createSheet(sheetPrefix[currentSection]
				+ (currentPage == 0 ? "" : String.valueOf(currentPage)));
		// Create Titles for the new Sheet
		HSSFRow row = currentSheet.createRow(0);
		for (int index = 0; index < columnInfo.size(); index++) {
			ColumnDescriber descriptor = columnInfo.get(index);
			HSSFCell cell = row.createCell(index);
			descriptor.title(workbook, cell);
		}
	}

	@Override
	protected void finalizeFile() throws IOException {
		OutputStream os = getFileOutputStream();
		workbook.write(os);
		os.close();
	}

	static final String EVEN_NUMBER = "even.number";

	static final String ODD_NUMBER = "odd.number";

	static final String EVEN_TEXT = "even.text";

	static final String ODD_TEXT = "odd.text";

	static final String TITLE = "title";

	protected Map<String, HSSFCellStyle> styles;

	@Override
	protected void reset() {
		super.reset();
		styles = null;
		workbook = null;
		currentSheet = null;
	}
	
	protected List<ColumnDescriber> columninfo(String[] colNames) {
		if (styles == null) {
			// Create Font for content and title
			// Create Font here rather than in each column formatter in order to
			// save font space
			HSSFFont titleFont = workbook.createFont();
			if (getFont() != null) {
				titleFont.setFontName(getFont().fontName);
				titleFont.setFontHeightInPoints(getFont().size);
			}
			titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			HSSFFont font = workbook.createFont();
			if (getFont() != null) {
				font.setFontName(getFont().fontName);
				font.setFontHeightInPoints(getFont().size);
			}
			// Prepare the styles
			// TODO extract style from decorations

			styles = new HashMap<String, HSSFCellStyle>();

			HSSFCellStyle titleStyle = workbook.createCellStyle();
			titleStyle.setFont(titleFont);
			titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			styles.put(TITLE, titleStyle);

			HSSFCellStyle evenNumberStyle = workbook.createCellStyle();
			evenNumberStyle.setFont(font);
			// To resolve issue about \n won't display properly.
			// See Bug IPS00005514 
			evenNumberStyle.setWrapText(true);
			if (usecolor) {
				evenNumberStyle
						.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
				evenNumberStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			}
			evenNumberStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			styles.put(EVEN_NUMBER, evenNumberStyle);

			HSSFCellStyle oddNumberStyle = workbook.createCellStyle();
			oddNumberStyle.setFont(font);
			oddNumberStyle.setWrapText(true);
			if (usecolor) {
				oddNumberStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				oddNumberStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
			}
			oddNumberStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			styles.put(ODD_NUMBER, oddNumberStyle);

			HSSFCellStyle evenTextStyle = workbook.createCellStyle();
			evenTextStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			evenTextStyle.setWrapText(true);
			if (usecolor) {
				evenTextStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				evenTextStyle
						.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
			}
			styles.put(EVEN_TEXT, evenTextStyle);

			HSSFCellStyle oddTextStyle = workbook.createCellStyle();
			oddTextStyle.setWrapText(true);
			if (usecolor) {
				oddTextStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				oddTextStyle
						.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			}
			oddTextStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

			styles.put(ODD_TEXT, oddTextStyle);
		}

		List<ColumnDescriber> describers = new ArrayList<ColumnDescriber>();
		for (int i = 0; i < colNames.length; i++) {
			ColumnDescriber cd = new ColumnDescriber(colNames[i]);
			describers.add(cd);
		}

		return describers;
	}

	/**
	 * This format descriptor take the content as string and all parameters as
	 * default value(left alignment..,etc)
	 * 
	 * @author Harper Jiang
	 * 
	 */

	public class ColumnDescriber {

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public ColumnDescriber(String name) {
			super();
			setName(name);
		}

		public String getCellStyle(HSSFWorkbook workbook, Object value,
				boolean even) {
			return value instanceof Number ? (even ? EVEN_NUMBER : ODD_NUMBER)
					: (even ? EVEN_TEXT : ODD_TEXT);
		}

		public String getTitleStyle(HSSFWorkbook workbook) {
			return TITLE;
		}

		public void describe(HSSFWorkbook workbook, HSSFCell cell, Object value) {
			if (value == null)
				return;
			HSSFCellStyle cs = styles.get(getCellStyle(workbook, value,
					currentRow % 2 == 0));
			cell.setCellStyle(cs);
			if (value instanceof Number) {
				cell.setCellValue(((Number) value).doubleValue());
			} else {
				cell
						.setCellValue(new HSSFRichTextString(String
								.valueOf(value)));
			}
		}

		public void title(HSSFWorkbook workbook, HSSFCell cell) {
			cell.setCellValue(new HSSFRichTextString(getName()));
			cell.setCellStyle(styles.get(getTitleStyle(workbook)));
		}

	}

}
