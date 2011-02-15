package org.harper.frm.data.formatter.excel.xmlbeans;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.harper.frm.core.tools.bean.Bean2XMLSerializer;
import org.harper.frm.data.formatter.excel.xmlbeans.Data;
import org.harper.frm.data.formatter.excel.xmlbeans.DocumentProperties;
import org.harper.frm.data.formatter.excel.xmlbeans.ExcelXMLBeanFactory;
import org.harper.frm.data.formatter.excel.xmlbeans.Style;
import org.harper.frm.data.formatter.excel.xmlbeans.Styles;
import org.harper.frm.data.formatter.excel.xmlbeans.Workbook;
import org.junit.Test;

public class ExcelXMLTest {

	@Test
	public void testWorkbook() throws Exception {
		Workbook book = new Workbook();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Bean2XMLSerializer.beginTag(baos, book, "Workbook");

		assertEquals(
				"<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\" xmlns:html=\"http://www.w3.org/TR/REC-html40\">",
				baos.toString());
	}

	@Test
	public void testDocumentProperties() throws Exception {
		DocumentProperties docProp = ExcelXMLBeanFactory
				.createDocumentProperties();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Bean2XMLSerializer.serialize(baos, docProp, "DocumentProperties");

		assertEquals(
				"<DocumentProperties><Author>Harper Jiang</Author><LastAuthor>Harper Jiang</LastAuthor><Company>OOCL</Company><Version>11.8132</Version></DocumentProperties>",
				baos.toString());
	}

	@Test
	public void testStyles() throws Exception {
		Styles styles = new Styles();
		styles.Style = new ArrayList<Style>();

		Style style = ExcelXMLBeanFactory.createDefaultStyle();

		styles.Style.add(style);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Bean2XMLSerializer.serialize(baos, styles, "Styles");

		assertEquals(
				"<Styles><Style ss:ID=\"Default\" ss:Name=\"Normal\"><Alignement></Alignement><Borders></Borders><Font x:Family=\"Swiss\"></Font><Interior ss:Pattern=\"Solid\"></Interior><NumberFormat></NumberFormat><Protection></Protection></Style></Styles>",
				baos.toString());
	}

	@Test
	public void testData() throws Exception {
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(
				"2009-06-16 13:07:38.315", new ParsePosition(0));
		Data data = ExcelXMLBeanFactory.createData(date);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Bean2XMLSerializer.serialize(baos, data);
		assertEquals(
				"<Data ss:Type=\"DateTime\">2009-06-16T13:07:38.315</Data>",
				baos.toString());
	}
}
