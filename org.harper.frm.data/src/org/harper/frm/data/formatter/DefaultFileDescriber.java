package org.harper.frm.data.formatter;

import org.harper.frm.data.Extension;
import org.harper.frm.data.IFile;
import org.harper.frm.data.IFileDescriber;

/**
 * Supporting use a string including date pattern to describe dynamic file
 * names. For example, if the file name comprise of a constant prefix and
 * current date with format "YYYY-MM-dd HH24:mm", the pattern is like the
 * following : $name_$date{YYYY-MM-dd HH24:mm}
 * 
 * 
 * @author Harper Jiang
 * @version 1.0 2009-03-16
 * @since Components 1.0
 * @see FormatterUtils
 */
public class DefaultFileDescriber implements IFileDescriber {

	private final String fileName;

	private final Extension extension;

	private final String pattern;

	public DefaultFileDescriber(String pattern) {
		this("", null, pattern);
	}

	public DefaultFileDescriber(String fileName, Extension extension,
			String pattern) {
		super();
		this.fileName = fileName;
		this.extension = extension;
		this.pattern = pattern;
	}

	public String getName() {
		return FormatterUtils.formatTagName(fileName,pattern);
	}

	public IFile describe(IFile file) {
		file.setExtension(extension.getText());
		file.setMimeType(extension.getType().fullName());
		file.setName(getName());
		return file;
	}

}
