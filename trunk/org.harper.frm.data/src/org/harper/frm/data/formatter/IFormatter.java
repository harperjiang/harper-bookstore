package org.harper.frm.data.formatter;

import java.io.IOException;

import org.harper.frm.data.IFile;
import org.harper.frm.data.IFileDescriber;
import org.harper.frm.data.parser.IParser;


/**
 * 
 * While {@link IParser} parse input from a file to data models, the
 * <code>IFormatter</code> do the opposite thing : it formats the data model
 * into various file formats: Plain text, HTML, PDF, XML, Microsoft Files or
 * something else. The parsed file can be sent out or stored for later
 * processing.
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since Component 1.1
 * @see IFile
 * 
 */
public interface IFormatter {

	/**
	 * Get associated file describer
	 * 
	 * @return the describer
	 */
	public IFileDescriber getDescriber();

	/**
	 * Format the given data model into a file.
	 * 
	 * @param input
	 *            input data model
	 * 
	 * @throws UnsupportedOperationException
	 *             If the data model is not supported by the given formatter.
	 * @throws IOException
	 *             If IO problem occursing while processing the data / file.
	 */
	public IFile format(Object input) throws IOException,
			UnsupportedOperationException;
	
	/**
	 * 
	 * @param listener
	 */
	public void addPageListener(FormatListener listener);
	
	/**
	 * 
	 * @param listener
	 */
	public void removePageListener(FormatListener listener);
}
