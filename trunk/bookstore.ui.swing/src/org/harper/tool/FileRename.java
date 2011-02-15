package org.harper.tool;

import java.io.File;

import javax.swing.JFileChooser;

public class FileRename {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (JFileChooser.APPROVE_OPTION == chooser.showDialog(null, "Select")) {
			new FileRename().process(chooser.getSelectedFile());
		}
	}

	public void process(File dir) {
		if(!dir.isDirectory())
			return;
		for (File file : dir.listFiles()) {
			if (file.isDirectory())
				process(file);
			else
				file.renameTo(new File(file.getAbsolutePath().toLowerCase()));
		}
	}
}
