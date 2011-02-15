package org.harper.frm.data;

import org.harper.frm.data.MappedFile;
import org.junit.Test;


public class MappedFileTest {

	@Test
	public void testGetOutputStream() throws Exception {
		MappedFile mf = new MappedFile();
		mf.setAbsolutePath("D:/mappedFile");

		for (int i = 0; i < 10000; i++) {
			mf.getOutputStream(true).write("map".getBytes());

		}
		mf.getOutputStream(true).close();
	}

	@Test
	public void testWriteThenReadTempFile() throws Exception {
		MappedFile mf = new MappedFile(true);
		System.out.println(mf.getAbsolutePath());
		for (int i = 0; i < 1000; i++) {
			mf.getOutputStream(true).write(
					("This is a map data" + i).getBytes());
		}
		mf.getOutputStream(true).close();
		
		byte[] buffer = new byte[1000];
		while(mf.getInputStream().read(buffer) == 1000) {
			System.out.println(new String(buffer));
		}
		mf.getInputStream().close();
	}
	
	@Test
	public void testMemoryUsage() throws Exception {
		MappedFile mf = new MappedFile(true);
		
		for(int i = 0 ; i < 1000000; i ++) 
			mf.getOutputStream(true).write("This is a content".getBytes());
		mf.getOutputStream(true).close();
	}

}
