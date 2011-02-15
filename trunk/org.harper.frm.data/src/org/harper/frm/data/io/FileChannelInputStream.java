package org.harper.frm.data.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * <p>
 * The class <code>FileChannelInputStream</code> is a simple wrapper of a file
 * channel to be compliant with {@link InputStream} families that were widely
 * used.
 * </p>
 * <p>
 * NOTICE: For simplicity concern, current <code>FileChannelOutputStream</code>
 * doesn't support random access, which means only write(byte[]) is accessible.
 * Invoking other methods such as {@link InputStream#read()} may either has no
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
 * 
 * @see FileChannelOutputStream
 * @see FileResource
 */
public class FileChannelInputStream extends InputStream implements
		FileChannelConstants, IFileChannelOwner {

	private FileChannel channel;

	private ByteBuffer buffer;

	public FileChannelInputStream(FileChannel channel) throws IOException {
		super();
		this.channel = channel;
		buffer = channel.map(MapMode.READ_ONLY, 0, channel.size());
	}

	public FileChannel getChannel() {
		return channel;
	}

	@Override
	public int read(byte b[], int off, int len) throws IOException {
		int readed = Math.min(len, buffer.remaining());
		buffer.get(b, off, readed);
		return readed;
	}

	@Override
	public int read() throws IOException {
		throw new UnsupportedOperationException("Not Supported");
	}

	@Override
	public void close() throws IOException {
		buffer = null;
		getChannel().close();
	}
}
