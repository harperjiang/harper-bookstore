package org.harper.frm.data.formatter;

import java.io.IOException;

import org.harper.frm.data.Extension;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEvent;
import com.lowagie.text.pdf.PdfWriter;

/**
 * 
 * @author Harper Jiang
 * @since ips.frm.component 1.0
 * @version 1.0 Jun 17, 2009
 */
public class PDFFormatter extends DefaultFormatter {

	Document document;

	PdfPTable datatable;

	static final int ROW_TO_FLUSH = 100;

	/**
	 * 
	 * @param fileName
	 */
	public PDFFormatter(String fileName) {
		super();
		setPageSize(ROW_TO_FLUSH);
		setDescriber(new DefaultFileDescriber(fileName, Extension.pdf, null));
	}

	/**
	 * 
	 * @param fileName
	 * @param namePatter
	 */
	public PDFFormatter(String fileName, String namePattern) {
		super();
		setPageSize(ROW_TO_FLUSH);
		setDescriber(new DefaultFileDescriber(fileName, Extension.pdf,
				namePattern));
	}

	@Override
	protected void createFile() throws IOException {
		// TODO Adjust the document parameter according to decoration
		document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);

		try {
			PdfWriter writer = PdfWriter.getInstance(document, this
					.getFileOutputStream());
			writer.setPageEvent(new PdfPageEventHandler());
			document.open();
		} catch (DocumentException e) {
			// TODO Log exception
			throw new RuntimeException(e);
		}

	}

	@Override
	protected void newSection(String[] sectionHeader) throws IOException {
		if (null != datatable)
			try {
				document.add(datatable);
			} catch (DocumentException e1) {
				// TODO Log
				throw new RuntimeException(e1);
			}
		datatable = new PdfPTable(sectionHeader.length);
		datatable.setWidthPercentage(100);
		datatable.getDefaultCell().setPadding(3);
		datatable.getDefaultCell().setBorderWidth(2);

		datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		for (int i = 0; i < sectionHeader.length; i++)
			datatable.addCell(sectionHeader[i]);

		datatable.setHeaderRows(1);
	}

	@Override
	protected void newPage() throws IOException {
		if (null == datatable)
			return;
		try {
			document.add(datatable);
		} catch (DocumentException e) {
			// TODO Log
			throw new RuntimeException(e);
		}
		datatable.deleteBodyRows();
		datatable.setSkipFirstHeader(true);
	}

	@Override
	protected void newRow(Object input, ITableContentProvider<?> provider)
			throws IOException {
		int columns = datatable.getNumberOfColumns();
		if (currentRow % 2 == 1) {
			datatable.getDefaultCell().setGrayFill(0.9f);
		}
		for (int i = 0; i < columns; i++) {
			Object cellElement = provider.get(input, i);
			datatable.addCell(String.valueOf(cellElement));
		}
		if (currentRow % 2 == 1) {
			datatable.getDefaultCell().setGrayFill(1);
		}
	}

	@Override
	protected void finalizeFile() throws IOException {
		try {
			if (null != datatable)
				document.add(datatable);
		} catch (DocumentException e) {
			// TODO Log
			throw new RuntimeException(e);
		}
		document.close();
		datatable = null;
		document = null;
	}

	protected class PdfPageEventHandler implements PdfPageEvent {

		public void onChapter(PdfWriter writer, Document document,
				float paragraphPosition, Paragraph title) {

		}

		public void onChapterEnd(PdfWriter writer, Document document,
				float paragraphPosition) {
		}

		public void onCloseDocument(PdfWriter writer, Document document) {
		}

		public void onEndPage(PdfWriter writer, Document document) {

		}

		public void onGenericTag(PdfWriter writer, Document document,
				Rectangle rect, String text) {

		}

		public void onOpenDocument(PdfWriter writer, Document document) {

		}

		public void onParagraph(PdfWriter writer, Document document,
				float paragraphPosition) {

		}

		public void onParagraphEnd(PdfWriter writer, Document document,
				float paragraphPosition) {

		}

		public void onSection(PdfWriter writer, Document document,
				float paragraphPosition, int depth, Paragraph title) {

		}

		public void onSectionEnd(PdfWriter writer, Document document,
				float paragraphPosition) {

		}

		public void onStartPage(PdfWriter writer, Document document) {

		}

	}
}
