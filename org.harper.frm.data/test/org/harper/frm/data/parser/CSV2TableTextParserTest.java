package org.harper.frm.data.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;

import org.harper.frm.data.MemoryTable;
import org.harper.frm.data.parser.CSV2TableParser;
import org.harper.frm.data.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CSV2TableTextParserTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	protected static String[] names = { "This is a title contains, ",
			"This is a tit,le contains", ",This is a title contains",
			"This is a normal Title", "\"This is a title contains",
			"This is a \"adsfa\" title", "This si a \"", "\"asdfwewe\"",
			"\"fasewe\"fawefwe", "fwefwefwe\"", "fwefwe\"fwefwe\"" };

	@Test
	public void testParse() throws Exception {
		String pattern1 = "\"This is a title contains, \",\"This is a tit,le contains\",\",This is a title contains\",This is a normal Title,\"\"\"This is a title contains\",\"This is a \"\"adsfa\"\" title\",\"This si a \"\"\",\"\"\"asdfwewe\"\"\",\"\"\"fasewe\"\"fawefwe\",\"fwefwefwe\"\"\",\"fwefwe\"\"fwefwe\"\"\"";
		CSV2TableParser parser = new CSV2TableParser();
		parser.setHeaderExists(true);
		MemoryTable table = (MemoryTable) parser
				.parse(new StringBufferInputStream(pattern1));
		assertEquals(11, table.getColumnCount());
		for (int i = 0; i < 11; i++)
			assertEquals(names[i], table.getColumnName(i));
	}

	@Test
	public void testpParseCRLF() throws Exception {
		String patternCRLF = "\"This is a content containing \n" + "CRLF\"";
		CSV2TableParser parser = new CSV2TableParser();
		parser.setHeaderExists(false);
		MemoryTable table = (MemoryTable) parser
				.parse(new StringBufferInputStream(patternCRLF));
		assertEquals(1, table.getColumnCount());
		assertEquals(1, table.getRowCount());
		assertEquals("This is a content containing \n" + "CRLF", table
				.getValueAt(0, 0));
	}

	@Test
	public void testReadLine() throws Exception {
		String pattern1 = "\"This is a title contains, \",\"This is a tit,le contains\",\",This is a title contains\",This is a normal Title,\"\"\"This is a title contains\",\"This is a \"\"adsfa\"\" title\",\"This si a \"\"\",\"\"\"asdfwewe\"\"\",\"\"\"fasewe\"\"fawefwe\",\"fwefwefwe\"\"\",\"fwefwe\"\"fwefwe\"\"\"";
		String patternCRLF = "\"This is a content containing \n" + "CRLF\"";
		String ptn = pattern1 + "\n" + patternCRLF;
		String line = new CSV2TableParser().readLine(new BufferedReader(
				new InputStreamReader(new StringBufferInputStream(ptn))));
		assertEquals(pattern1,line);
	}
	
	@Test
	public void testReadLine2() throws Exception {
		
		String patternCRLF = "\"This is a content containing \n" + "CRLF\"";
		String pattern2 = "\"\"\" \",,,,,,,,,,";
		String ptn = patternCRLF + "\n" + pattern2;
		String line = new CSV2TableParser().readLine(new BufferedReader(
				new InputStreamReader(new StringBufferInputStream(ptn))));
		assertEquals(patternCRLF,line);
	}

	@Test
	public void testErrorLine() throws Exception {
		String pattern1 = "\"abc \"32";
		CSV2TableParser parser = new CSV2TableParser();
		parser.setHeaderExists(true);
		try {
			MemoryTable table = (MemoryTable) parser
					.parse(new StringBufferInputStream(pattern1));
			System.out.println(table.getColumnCount());
			fail("Not here");
		} catch (ParseException e) {

		} 
	}

	protected static final String nameforcol(int column) {
		int j = column;
		String result = "";
		for (; j >= 0; j = j / 26 - 1) {
			result = (char) ((char) (j % 26) + 'A') + result;
		}
		return result;
	}
}
