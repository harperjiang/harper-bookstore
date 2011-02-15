package org.harper.frm.data.formatter;

import org.harper.frm.data.MappedFile;
import org.harper.frm.data.formatter.PDFFormatter;
import org.junit.Test;



public class PDFFormatterTest {
	@Test
	public void testPDFFormatter() throws Exception {
		PDFFormatter formatter = new PDFFormatter("Temp");
		MappedFile file = new MappedFile();
		file.setAbsolutePath("testpdf.pdf");
		formatter.setFile(file);
		formatter.setContentProvider(new SampleTableContentProvider());

		long start = System.currentTimeMillis();
		
		formatter.format(new Object[] { "abcdef", 1 });

		System.out.println(System.currentTimeMillis() - start);
	}
	
	@Test
	public void testSmallPDFFormatter() throws Exception {
		PDFFormatter formatter = new PDFFormatter("Temp");
		MappedFile file = new MappedFile();
		file.setAbsolutePath("testpdf2.pdf");
		formatter.setFile(file);
		formatter.setContentProvider(new SampleTableContentProvider2());

		long start = System.currentTimeMillis();
		
		formatter.format(new Object[] { "abcdef", 1 });

		System.out.println(System.currentTimeMillis() - start);
	}
}
