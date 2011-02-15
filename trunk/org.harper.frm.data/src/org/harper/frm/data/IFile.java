package org.harper.frm.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * This interface represents an logical structure that can be "written" out as a
 * file.
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since Component 1.0
 */
public interface IFile extends IDataModel {

	public int getMode();

	public void setMode(int mode);

	public String getName();

	public void setName(String name);

	public String getAbsolutePath();

	public void setAbsolutePath(String path);

	public String getExtension();

	public void setExtension(String extension);

	public String getMimeType();

	public void setMimeType(String type);

	public String getEncoding();

	public void setEncoding(String newEncoding);

	public long getSize();
	
	public InputStream getInputStream() throws IOException;

	/**
	 * 
	 * @param write
	 *            Indicate whether the return value will be used only for check
	 *            or as an output target.
	 * @return
	 * @throws IOException
	 */
	public OutputStream getOutputStream(boolean write) throws IOException;

	public Map<String, String> getMetaInfo();
}
