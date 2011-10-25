package org.harper.frm.gui.swing.comp.window;

import javax.swing.JDialog;
import javax.swing.JSplitPane;

import org.harper.frm.gui.swing.UIStandard;

public class PreferenceDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2064779632844999435L;

	JSplitPane splitPane;
	
	public PreferenceDialog() {
		super();
		setModal(true);
		setSize(800,600);
		UIStandard.standardDialog(this);
	}
	
	public void addPage(PreferencePage newPage) {

	}
}
