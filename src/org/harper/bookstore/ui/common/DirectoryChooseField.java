package org.harper.bookstore.ui.common;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DirectoryChooseField extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8297338127977560359L;

	private JTextField directoryField;

	private JButton chooseButton;

	private File selectedFile;
	
	private static volatile String lastDir;

	public DirectoryChooseField() {
		super();
//		setBorder(LineBorder.createBlackLineBorder());

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);

		directoryField = new JTextField();
		add(directoryField);

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.gridwidth = GridBagConstraints.RELATIVE;
		layout.setConstraints(directoryField, c);

		chooseButton = new JButton("...");
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		layout.setConstraints(chooseButton, c);
		chooseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				String oldText = directoryField.getText();
				if(oldText == null)
					oldText = lastDir;
				if (oldText != null) {
					try {
						File oldDir = new File(oldText);
						chooser.setCurrentDirectory(oldDir);
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				chooser.setMultiSelectionEnabled(false);
				
				int result = chooser.showDialog(getParentFrame(DirectoryChooseField.this), "Choose");
				if (JFileChooser.APPROVE_OPTION == result) {
					directoryField.setText(chooser.getSelectedFile()
							.getAbsolutePath());
					DirectoryChooseField.this.selectedFile = chooser
							.getSelectedFile();
					lastDir = selectedFile.getParent();
				}
			}
		});
		add(chooseButton);
	}

	public File getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}

	public static JFrame getParentFrame(Component comp) {
		if(null == comp)
			return null;
		if(comp instanceof JFrame)
			return (JFrame)comp;
		return getParentFrame(comp.getParent());
	}
}
