package org.harper.frm.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ConcurrentModificationException;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.lang.Validate;
import org.harper.frm.data.io.FileChannelInputStream;
import org.harper.frm.data.io.FileChannelOutputStream;


/**
 * <p>
 * <code>MappedFile</code> contains a reference to a {@link FileChannel} and use
 * it as the source/target of I/O.
 * </p>
 * <p>
 * NOTICE: Current implementation doesn't support using one MappedFile instance
 * to perform read/write operation at the same time. Doing this may cause
 * unexpected result.
 * </p>
 * <p>
 * NOTICE: Not Thread Safe
 * </p>
 * 
 * @author Harper Jiang
 * @version 1.0 2009-03-12
 * @since Component 1.1
 * 
 */
public class MappedFile extends AbstractFile {

	protected static final String MAPPED_FILE_PREFIX = "mapped";

	protected static final String MAPPED_FILE_SUFFIX = "tmp";

	private AtomicReference<FileChannel> fileChannel;

	public MappedFile() {
		super();
		fileChannel = new AtomicReference<FileChannel>();
		is = new AtomicReference<InputStream>();
		os = new AtomicReference<OutputStream>();
	}

	public MappedFile(boolean temp) throws IOException {
		this();
		if (temp) {
			// TODO Fetch File Dir and Create Temp File
			setAbsolutePath(File.createTempFile(MAPPED_FILE_PREFIX,
					MAPPED_FILE_SUFFIX).getAbsolutePath());
		}
	}

	public FileChannel getFileChannel() throws FileNotFoundException {
		FileChannel fc = fileChannel.get();
		if (fc == null || !fc.isOpen()) {
			FileChannel newfc = createChannel();
			if (!fileChannel.compareAndSet(fc, newfc))
				throw new ConcurrentModificationException();
		}
		return fileChannel.get();
	}

	protected FileChannel createChannel() throws FileNotFoundException {
		String absPath = getAbsolutePath();
		Validate.notNull(absPath);
		RandomAccessFile file = new RandomAccessFile(absPath, "rw");
		return file.getChannel();
	}

	/**
	 * Replace the MappedFile's current channel with the given one, current
	 * IOStream will be closed
	 * 
	 * @param newChannel
	 * @throws IOException
	 */
	public void setFileChannel(FileChannel newChannel) throws IOException {
		FileChannel oldChannel = fileChannel.get();
		if (oldChannel != newChannel) {
			getInputStream().close();
			getOutputStream(true).close();
		}
		this.fileChannel.set(newChannel);
	}

	private AtomicReference<InputStream> is;

	private AtomicReference<OutputStream> os;

	public InputStream getInputStream() throws IOException {
		if (is.get() == null) {
			FileChannel fc = getFileChannel();
			if (!is.compareAndSet(null, new FileChannelInputStream(fc) {
				/**
				 * Reset the stream when closed
				 */
				public void close() throws IOException {
					super.close();
					if (!is.compareAndSet(this, null))
						throw new ConcurrentModificationException();
				}

			}))
				throw new ConcurrentModificationException();
		}
		return is.get();
	}

	public OutputStream getOutputStream(boolean write) throws IOException {
		if (os.get() == null && write)
			if (!os.compareAndSet(null, new FileChannelOutputStream(
					getFileChannel()) {
				/**
				 * Reset the stream when closed
				 */
				public void close() throws IOException {
					super.close();
					if (!os.compareAndSet(this, null))
						throw new ConcurrentModificationException();
				}
			}))
				throw new ConcurrentModificationException();
		return os.get();
	}

}
