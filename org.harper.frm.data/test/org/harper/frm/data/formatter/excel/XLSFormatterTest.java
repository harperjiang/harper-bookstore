package org.harper.frm.data.formatter.excel;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.harper.frm.data.IFile;
import org.harper.frm.data.MemoryTable;
import org.harper.frm.data.formatter.excel.XLSFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class XLSFormatterTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTable2XLSFormatter() throws IOException {
		XLSFormatter xlsformatter = new XLSFormatter("FileName",
				"SheetName");

		MemoryTable mt = new MemoryTable(16);
		for (int j = 0; j < 200; j++) {
			String[] row = new String[16];
			for (int i = 0; i < 16; i++) {
				row[i] = String.valueOf(j)+":"+i;
			}
			mt.addRow(row);
		}
		
		IFile file = xlsformatter.format(mt);
		
		FileOutputStream fos = new FileOutputStream(file.getName());
		IOUtils.copy(file.getInputStream(),fos);
		fos.close();
		file.getInputStream().close();
		
	}


}
