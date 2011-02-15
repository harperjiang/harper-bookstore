package org.harper.frm.data.io;


import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * When used together with <code>FileResource</code>, this class will be
 * recognized and the underling {@link FileChannel} will be accessed directly to
 * improve effeciency. </p>
 * 
 * @author Harper Jiang
 * @version 1.0 2009-03-12
 * @since Component 1.1
 * 
 * @see FileChannelOutputStream
 * @see FileResource
 */
public interface IFileChannelOwner {

	public FileChannel getChannel() throws IOException;
}
