package org.harper.frm.data.formatter.excel;

import org.harper.frm.data.MappedFile;
import org.harper.frm.data.formatter.SampleTableContentProvider;
import org.harper.frm.data.formatter.excel.ExcelXMLFormatter;
import org.junit.Test;



public class ExcelXMLFormatterTest {

	@Test
	public void testExcelXMLFormatter() throws Exception {
		ExcelXMLFormatter formatter = new ExcelXMLFormatter("Temp",
				new String[] { "SheetX", "SheetY" });
		MappedFile file = new MappedFile();
		file.setAbsolutePath("testxml.xml");
		formatter.setFile(file);
		formatter.setContentProvider(new SampleTableContentProvider());
		formatter.setUsecolor(true);
		
		long start = System.currentTimeMillis();
		
		formatter.format(new Object[] { "abcdef", 1 });

		System.out.println(System.currentTimeMillis() - start);
	}
}
