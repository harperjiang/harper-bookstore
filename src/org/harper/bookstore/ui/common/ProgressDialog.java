package org.harper.bookstore.ui.common;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * A Dialog that shows a undetermined Progress Bar and cannot be closed. Used to
 * block user input while performing backend operation.
 * 
 * @author Harper
 * 
 */
public class ProgressDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6631674283522110654L;
	private JLabel label;

	public ProgressDialog(JFrame parent, String text) {
		super(parent);
		setSize(150, 50);
		if (null == parent) {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation((int) (screenSize.getWidth() - getWidth()) / 2,
					(int) (screenSize.getHeight() - getHeight()) / 2);
		} else {
			setLocation((parent.getWidth() - getWidth()) / 2,
					(parent.getHeight() - getHeight()) / 2);
		}
		setModal(true);
		setUndecorated(true);
		setLayout(new FlowLayout());
		label = new JLabel(text);
		label.setPreferredSize(new Dimension(145, 20));
		add(label);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(145, 20));
		progressBar.setIndeterminate(true);
		add(progressBar);

		setAlwaysOnTop(true);
	}

	public void setText(String text) {
		label.setText(text);
	}

}
