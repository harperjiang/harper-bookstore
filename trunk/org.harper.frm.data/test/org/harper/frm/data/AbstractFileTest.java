package org.harper.frm.data;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.harper.frm.data.AbstractFile;
import org.junit.Test;


public class AbstractFileTest {

	static class DemoFile extends AbstractFile {

		public InputStream getInputStream() throws IOException {
			return null;
		}

		public OutputStream getOutputStream(boolean write) throws IOException {
			return null;
		}
		
	}
	
	@Test
	public void testSetName() {
		DemoFile df = new DemoFile();
		df.setName("abcdefg.xls");
		assertEquals("xls",df.getExtension());
		assertEquals(null,df.getMimeType());
	}

}
