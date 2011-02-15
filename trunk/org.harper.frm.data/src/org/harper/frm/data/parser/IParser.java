package org.harper.frm.data.parser;


import java.io.IOException;
import java.io.InputStream;

import org.harper.frm.data.IFile;


/**
 * 
 * An <code>IParser</code> instance knows how to parse a given input stream to
 * data models.
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since Component 1.1
 * @see IFile
 * 
 */
public interface IParser {

	/**
	 * Parse the content of the given input to models.
	 * 
	 * @param input
	 *            Input stream that contains the content to be parsed.
	 * @return Parsed model.
	 * @throws IOException if IO exception occurs while reading input stream
	 * @throws UnsupportedOperationException
	 *             If the given model type can not be recognized by the parser.
	 * @throws ParseException
	 *             If unexpected exception occurs while parsing the
	 *             content(generally caused by the parser or lexer.)
	 */
	public Object parse(InputStream input) throws IOException, ParseException,
			UnsupportedOperationException;

}
