package org.harper.frm.gui.swing.comp.dialog;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.apache.commons.lang.Validate;

public class ExtensionFileFilter extends FileFilter {

	private String extension;

	private String description;

	public ExtensionFileFilter(String ext, String desc) {
		super();
		Validate.notNull(ext);
		Validate.notNull(desc);
		extension = ext;
		description = desc;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean accept(File f) {
		if (f.isDirectory())
			return true;
		if (!f.getName().contains("."))
			return false;
		return f.getName().substring(f.getName().lastIndexOf('.')).equals(
				extension);
	}

	@Override
	public String getDescription() {
		return description;
	}

	public String processFileName(String oldName) {
		if(oldName.endsWith(getExtension()))
			return oldName;
		return oldName + getExtension();
	}
}
