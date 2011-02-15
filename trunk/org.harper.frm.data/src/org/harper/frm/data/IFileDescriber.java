package org.harper.frm.data;

import org.harper.frm.data.formatter.IFormatter;

/**
 * 
 * Used together with {@link IFormatter}, <code>IFileDescriber</code> knows how
 * to add additional info besides a data stream procedured by formatter to form
 * an instance of {@link IFile} by providing it with file name, mime-type and
 * other required info.
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since Component 1.1
 * @see IFile
 * 
 */
public interface IFileDescriber {
	
	public String getName();

	public IFile describe(IFile file);
}
