package org.harper.bookstore.ui.main;

import javax.swing.SwingUtilities;

import org.harper.bookstore.ui.common.ProgressDialog;
import org.harper.bookstore.ui.common.UIStandard;
import org.harper.frm.toplink.SessionManager;

public class Launcher {

	private ProgressDialog dialog;

	public Launcher() {
		dialog = new ProgressDialog(null, null);
	}

	public void start(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dialog.setText(text);
				dialog.setVisible(true);
			}
		});
	}

	public void step(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (dialog.isVisible()) {
					dialog.setText(text);
				}
			}
		});
	}

	public void stop() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (dialog.isActive())
					dialog.dispose();
			}
		});
	}

	public static void main(String[] args) throws Exception {

		Launcher launcher = new Launcher();

		launcher.start("Initializing...");

		launcher.step("Connect to Database...");
		// Init DB connection
		SessionManager.getInstance().getSession();
		// TODO Get cache from server side
		launcher.step("Refreshing local data...");

		// Start GUI
		// Hide Dialog
		launcher.stop();

		UIStandard.setDefaultFont();
		new MainFrame();
	}
}
