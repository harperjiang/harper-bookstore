package org.harper.frm.data.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.harper.frm.data.DataModelHelper;


/**
 * <p>
 * The class <code>FileChannelOutputStream</code> is a simple wrapper of a file
 * channel to be compliant with {@link OutputStream} families that were widely
 * used.
 * </p>
 * <p>
 * NOTICE: For simplicity concern, current <code>FileChannelOutputStream</code>
 * doesn't support random access, which means only write(byte[]) is accessible.
 * Invoking other methods such as {@link OutputStream#write()} may either has no
 * effect or cause an {@link UnsupportedOperationException}. Implementation to
 * those may be added later.
 * </p>
 * <p>
 * When used together with <code>FileResource</code>, this class will be
 * recognized and the underling {@link FileChannel} will be accessed directly to
 * improve effeciency.
 * </p>
 * 
 * @author Harper Jiang
 * @version 1.0 2009-03-12
 * @since Component 1.1
 * @see FileChannelInputStream
 * @see FileResource
 */
public class FileChannelOutputStream extends OutputStream implements
		FileChannelConstants, IFileChannelOwner {

	private FileChannel channel;

	private ByteBuffer buffer;

	private long pos = 0;

	public FileChannelOutputStream(FileChannel channel) throws IOException {
		super();
		this.channel = channel;
		buffer = channel.map(MapMode.READ_WRITE, 0, BULK_SIZE);
	}

	public FileChannel getChannel() {
		return channel;
	}

	@Override
	public void write(byte b[], int off, int len) throws IOException {
		int left = len - off;
		int written = 0;
		while (left > 0) {
			int toWrite = Math.min(buffer.remaining(), left);
			buffer.put(b, off + written, toWrite);
			pos += toWrite;
			if (buffer.remaining() == 0) {
				// Map to next BULK
				buffer = channel.map(MapMode.READ_WRITE, pos, BULK_SIZE);
			}
			left -= toWrite;
			written += toWrite;
		}
	}

	@Override
	public void write(int b) throws IOException {
		throw new UnsupportedOperationException("Not supported");
	}

	/**
	 * <a
	 * href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4724038">Sun's
	 * Bug</a> See how they just left this bug open.... <quote>We'd be thrilled
	 * if someone could come up with a workable solution, so we'll leave this
	 * bug open in the hope that it will attract attention from someone more
	 * clever than we are. </quote>
	 */
	@Override
	public void close() throws IOException {
		channel.force(true);
		// buffer.flip();

		// RandomAccessFile root = AccessController
		// .doPrivileged(new PrivilegedAction<RandomAccessFile>() {
		// public RandomAccessFile run() {
		// try {
		// Field parentField = channel.getClass().getDeclaredField(
		// "parent");
		// parentField.setAccessible(true);
		// RandomAccessFile parent = (RandomAccessFile) parentField
		// .get(channel);
		// return parent;
		// } catch (Exception e) {
		// throw new RuntimeException(e);
		// }
		// }
		// });

		// root.getChannel().truncate(pos);
		channel.close();
		DataModelHelper.clean(buffer);

		// Reopen the channel
		// File file = new File(root.getFD());
		buffer = null;
	}
}
