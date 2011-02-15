package org.harper.frm.data.io;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.harper.frm.data.io.FileChannelOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class FileChannelOutputStreamTest {

	FileChannelOutputStream os;

	static final int count = 0;

	@Before
	public void setUp() throws Exception {
		// os = new
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWriteByteArray() throws Exception {
		File f = new File("mappedFile");
		RandomAccessFile raf = new RandomAccessFile(f, "rw");
		os = new FileChannelOutputStream(raf.getChannel());

		for (int i = 0; i < 5000; i++) {
			os.write(("This is a String"+i).getBytes());
		}
		os.close();
		raf.close();
	}

	@Test
	public void testFileChannel() throws Exception {
		File f = new File("mappedFile");
		RandomAccessFile raf = new RandomAccessFile(f, "rw");
		ByteBuffer bb = ByteBuffer.allocate(50);
		for (int i = 0; i < 10; i++) {
			bb.clear();
			bb.put(("Location is "+i).getBytes());
			bb.flip();
			raf.getChannel().position(i * 20);
			raf.getChannel().write(bb);
		}
		raf.getChannel().force(true);
		raf.getChannel().close();
		raf.close();
	}
	
	@Test
	public void testMappedWrite() throws Exception {
		File f = new File("mappedFile");
		RandomAccessFile raf = new RandomAccessFile(f, "rw");
		FileChannel fc = raf.getChannel();
		ByteBuffer bb = fc.map(MapMode.READ_WRITE, 0, 2000);
		for (int i = 0; i < 55; i++) {
			bb.put(("New version :Location is "+i).getBytes());
		}
		bb = fc.map(MapMode.READ_WRITE, 2000, 2000);
		for (int i = 0; i < 55; i++) {
			bb.put(("New version :Location is "+i).getBytes());
		}
		raf.getChannel().force(true);
		raf.getChannel().close();
		raf.close();
	}

}
