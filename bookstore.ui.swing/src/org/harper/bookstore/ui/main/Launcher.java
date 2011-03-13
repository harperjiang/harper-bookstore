package org.harper.bookstore.ui.main;

import org.harper.bookstore.MediatorInitializer;
import org.harper.bookstore.ui.cache.Cache;
import org.harper.bookstore.ui.common.ProgressDialog;
import org.harper.bookstore.ui.common.UIStandard;
import org.harper.frm.toplink.SessionManager;

public class Launcher {

	private ProgressDialog dialog;

	public Launcher() {
		dialog = new ProgressDialog(null, null);
	}

	public void start(final String text) {
		dialog.setText(text);
		dialog.setVisible(true);
	}

	public void step(final String text, Runnable run) {
		if (dialog.isVisible()) {
			dialog.setText(text);
		}
		Thread newThread = new Thread(run);
		newThread.start();
		try {
			newThread.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public void stop() {

		if (dialog.isActive())
			dialog.dispose();

	}

	public static void main(String[] args) throws Exception {
		UIStandard.setDefaultFont();

		Launcher launcher = new Launcher();

		launcher.start("Initializing...");

		// Init DB connection
		launcher.step("Connecting to Database...", new Runnable() {
			public void run() {
				SessionManager.getInstance().getSession();
			}
		});

		// TODO Get cache from server side
		launcher.step("Refreshing local data...", new Runnable() {
			public void run() {
				Cache.getInstance().load();
			}
		});

		launcher.step("Initializing Program Modules...", new Runnable() {
			public void run() {
				MediatorInitializer.init();
			}
		});
		// Start GUI
		// Hide Dialog
		launcher.stop();

		new MainFrame();
	}
}
