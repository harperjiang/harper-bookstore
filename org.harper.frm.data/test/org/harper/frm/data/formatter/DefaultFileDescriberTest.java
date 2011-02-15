package org.harper.frm.data.formatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.harper.frm.data.Extension;
import org.harper.frm.data.MemoryFile;
import org.harper.frm.data.MimeType;
import org.harper.frm.data.formatter.DefaultFileDescriber;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DefaultFileDescriberTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDescribe() {
		DefaultFileDescriber dfd = new DefaultFileDescriber("fileName",
				Extension.xls, "$name_$date{yyyy-MM-dd_HHmm}");
		MemoryFile file = new MemoryFile();
		dfd.describe(file);
		System.out.println(file.getName());
		assertEquals("xls", file.getExtension());
		assertEquals(MimeType.application_excel.fullName(), file.getMimeType());
		assertTrue(file.getName().startsWith("fileName_"));
	}
	@Test
	public void testDescribe2() {
		DefaultFileDescriber dfd = new DefaultFileDescriber("fileName",
				Extension.xls, "svoname_$date{yyyy-MM-dd_HHmm}");
		MemoryFile file = new MemoryFile();
		dfd.describe(file);
		System.out.println(file.getName());
		assertEquals("xls", file.getExtension());
		assertEquals(MimeType.application_excel.fullName(), file.getMimeType());
		assertTrue(file.getName().startsWith("svoname_"));
	}
	@Test
	public void testDescribe3() {
		DefaultFileDescriber dfd = new DefaultFileDescriber("",
				null, "svoname_$date{yyyy-MM-dd_HHmm}");
		
		assertTrue(dfd.getName().startsWith("svoname_"));
	}
	@Test
	public void testDescribe4() {
		DefaultFileDescriber dfd = new DefaultFileDescriber("",
				null, "UNCOMPLETESVO_$date{yyyy_MM_dd}");
		
		assertTrue(dfd.getName().startsWith("UNCOMPLETESVO"));
	}
	
	
}
