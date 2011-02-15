package org.harper.frm.data.io;

import static org.junit.Assert.assertEquals;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.harper.frm.data.io.FileChannelInputStream;
import org.junit.Test;


public class FileChannelInputStreamTest {

	@Test
	public void testReadByteArray() throws IOException {

		FileOutputStream fos = new FileOutputStream("mapinput");
		for (int i = 0; i < 10; i++)
			fos.write("mapinput".getBytes());
		fos.close();
		
		FileChannelInputStream fcis = new FileChannelInputStream(
				new RandomAccessFile("mapinput", "r")
						.getChannel());
		byte[] buffer = new byte[1000];
		fcis.read(buffer);
		assertEquals(buffer[0], (byte) 'm');
	}

}
