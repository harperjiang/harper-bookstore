package org.harper.frm.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang.Validate;

public class MemoryFile extends AbstractFile {

	private byte[] content;

	public MemoryFile() {
		super();
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	private OutputStream os;

	private InputStream is;

	public InputStream getInputStream() {
		Validate.notNull(content);
		if (is == null)
			is = new ByteArrayInputStream(content) {
				public void close() throws IOException {
					is = null;
				}
			};
		return is;
	}

	public OutputStream getOutputStream(boolean write) {
		if (os == null && write)
			os = new ByteArrayOutputStream() {
				public void close() throws IOException {
					// Will using the OutputStream buf directly cause
					// problem? The answer is yes...look into this method and it
					// maintains a count...use it directly will case invalid
					// file end( a lot of unused bytes)
					content = this.toByteArray();
					os = null;
				}
			};
		return os;
	}

}
