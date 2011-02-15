package org.harper.frm.data.formatter.excel;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.harper.frm.data.Extension;
import org.harper.frm.data.IFile;
import org.harper.frm.data.MappedFile;
import org.harper.frm.data.MimeType;
import org.harper.frm.data.formatter.SampleTableContentProvider;
import org.harper.frm.data.formatter.SampleTableContentProvider3;
import org.harper.frm.data.formatter.excel.XLSFormatter2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class XLSFormatter2Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testTable2XLSFormatter2() throws IOException {
		XLSFormatter2 formatter = new XLSFormatter2("Temp",
				new String[] { "SheetX" });
		MappedFile file = new MappedFile();
		file.setAbsolutePath("demo.xls");
		formatter.setFile(file);
		formatter.setContentProvider(new SampleTableContentProvider());
//		formatter.setUsecolor(true);
		

		long start = System.currentTimeMillis();
		
		IFile res = formatter.format(new Object[] { "abcdef"});
		
		assertEquals(Extension.xls.name(),res.getExtension());
		assertEquals(MimeType.application_excel.fullName(),res.getMimeType());

		System.out.println(System.currentTimeMillis() - start);
	}
	
	@Test
	public void testTitleContainsPoint() throws IOException {
		XLSFormatter2 formatter = new XLSFormatter2("Temp",
				new String[] { "SheetX" });
		MappedFile file = new MappedFile();
		file.setAbsolutePath("demoPoint.xls");
		formatter.setFile(file);
		formatter.setContentProvider(new SampleTableContentProvider3());
//		formatter.setUsecolor(true);

		long start = System.currentTimeMillis();
		
		IFile res = formatter.format(new Object[] { "abcdef"});
		
		assertEquals(Extension.xls.name(),res.getExtension());
		assertEquals(MimeType.application_excel.fullName(),res.getMimeType());

		System.out.println(System.currentTimeMillis() - start);
	}
	
}
