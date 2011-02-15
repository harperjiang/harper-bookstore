package org.harper.frm.data.formatter.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.harper.frm.data.Extension;
import org.harper.frm.data.IFile;
import org.harper.frm.data.ITable;
import org.harper.frm.data.MemoryFile;
import org.harper.frm.data.formatter.AbstractFormatter;
import org.harper.frm.data.formatter.DefaultFileDescriber;
import org.harper.frm.data.formatter.supp.Decoration;
import org.harper.frm.data.formatter.supp.DecorationInfo;
import org.harper.frm.data.formatter.supp.FontBean;

/**
 * Convert table to Microsoft Excel XLS file
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since frm.component 1.0
 */
public class XLSFormatter extends AbstractFormatter {

	public static final int MAX_ROW = 65000;

	private Decoration deco;

	public Decoration getDeco() {
		return deco;
	}

	public void setDeco(Decoration deco) {
		this.deco = deco;
	}

	
	protected FontBean getFont() {
		if (getDeco() != null)
			return getDeco().getBasic().font;
		return null;
	}

	private final String sheetPrefix;

	public String getSheetPrefix() {
		return sheetPrefix;
	}

	public XLSFormatter(String fileName, String sheetPrefix) {
		super();
		setDescriber(new DefaultFileDescriber(fileName, Extension.xls, null));
		this.sheetPrefix = sheetPrefix;
	}

	public XLSFormatter(String fileName, String sheetPrefix,
			String namePattern) {
		super();
		setDescriber(new DefaultFileDescriber(fileName, Extension.xls,
				namePattern));
		this.sheetPrefix = sheetPrefix;
	}

	public IFile format(Object input) throws IOException {

		Validate.isTrue(input instanceof ITable);

		ITable tablemodel = (ITable) input;

		// Create Excel Workbook
		HSSFWorkbook workbook = new HSSFWorkbook();

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

		// initialize columns
		List<ColumnDescriber> currentColumns = columninfo(tablemodel,
				titleFont, font);

		int sheetCount = 1;
		int tableRowPointer = 0;
		int rowCount = 1;
		boolean moreSheet = true;
		while (moreSheet) {
			moreSheet = false;
			// Create Excel Sheet
			HSSFSheet sheet = workbook.createSheet(getSheetPrefix()
					+ sheetCount);

			// Create Title
			HSSFRow row = sheet.createRow(0);
			for (int index = 0; index < currentColumns.size(); index++) {
				ColumnDescriber descriptor = currentColumns.get(index);
				HSSFCell cell = row.createCell(index);
				descriptor.setTitleFont(titleFont);
				descriptor.setFont(font);
				descriptor.title(workbook, cell);
			}

			while (tableRowPointer < tablemodel.getRowCount()) {
				row = sheet.createRow(rowCount);

				for (int index = 0; index < currentColumns.size(); index++) {
					Object value = tablemodel
							.getValueAt(tableRowPointer, index);
					ColumnDescriber descriptor = currentColumns.get(index);
					HSSFCell cell = row.createCell(index);
					descriptor.describe(workbook, cell, value);
				}
				rowCount++;
				tableRowPointer++;
				// When ROW COUNT exceeds MAX_ROW, change to another sheet.
				if (rowCount > MAX_ROW) {
					rowCount = 1;
					sheetCount += 1;
					moreSheet = true;
					break;
				}
			}
		}
		MemoryFile file = new MemoryFile();
		OutputStream os = file.getOutputStream(true);
		workbook.write(os);
		os.close();

		if (getDescriber() != null)
			getDescriber().describe(file);
		return file;
	}

	protected List<ColumnDescriber> columninfo(ITable table,
			HSSFFont titleFont, HSSFFont font) {
		// First try to extract info from Decorations
		Decoration deco = getDeco();
		List<DecorationInfo> infos = null;
		if (deco != null)
			infos = deco.getFeatureInfo(Decoration.FEATURE_TH);
		List<ColumnDescriber> describers = new ArrayList<ColumnDescriber>();
		if (infos != null) {
			for (DecorationInfo decoinfo : infos) {
				ColumnDescriber cd = new ColumnDescriber(decoinfo.name);
				cd.setTitleFont(titleFont);
				cd.setFont(font);
				describers.add(cd);
			}
		} else {
			for (int i = 0; i < table.getColumnCount(); i++) {
				ColumnDescriber cd = new ColumnDescriber(table.getColumnName(i));
				cd.setTitleFont(titleFont);
				cd.setFont(font);
				describers.add(cd);
			}
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

	public static class ColumnDescriber {

		private String name;

		private HSSFFont titleFont;

		private HSSFFont font;

		public HSSFFont getTitleFont() {
			return titleFont;
		}

		public void setTitleFont(HSSFFont titleFont) {
			this.titleFont = titleFont;
		}

		public HSSFFont getFont() {
			return font;
		}

		public void setFont(HSSFFont font) {
			this.font = font;
		}

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

		private HSSFCellStyle cellStyle;

		public HSSFCellStyle getCellStyle(HSSFWorkbook workbook, Object value) {
			if (cellStyle == null) {
				cellStyle = workbook.createCellStyle();
				if (getFont() != null)
					cellStyle.setFont(getFont());
				if (value instanceof Number) {
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
				} else {
					cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
					cellStyle.setDataFormat(HSSFDataFormat
							.getBuiltinFormat("text"));
				}
			}
			return cellStyle;
		}

		public void setCellStyle(HSSFCellStyle cellStyle) {
			this.cellStyle = cellStyle;
		}

		private HSSFCellStyle titleStyle;

		public HSSFCellStyle getTitleStyle(HSSFWorkbook workbook) {
			if (titleStyle == null) {
				titleStyle = workbook.createCellStyle();
				if (getTitleFont() != null)
					titleStyle.setFont(getTitleFont());
			}
			return titleStyle;
		}

		public void setTitleStyle(HSSFCellStyle titleStyle) {
			this.titleStyle = titleStyle;
		}

		public void describe(HSSFWorkbook workbook, HSSFCell cell, Object value) {
			if (value == null)
				return;
			HSSFCellStyle cs = getCellStyle(workbook, value);
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
			setCellStyle(null);
			setTitleStyle(null);
			cell.setCellValue(new HSSFRichTextString(getName()));
			cell.setCellStyle(getTitleStyle(workbook));
		}

	}

}
