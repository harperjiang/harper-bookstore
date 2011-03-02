package org.harper.bookstore.ui.common;

import javax.swing.JProgressBar;

import org.harper.bookstore.job.JobMonitor;

public class JProgressBarJobMonitor implements JobMonitor {

	private JProgressBar progress;

	public JProgressBarJobMonitor(JProgressBar progress) {
		this.progress = progress;
	}

	@Override
	public void start(int total) {
		progress.setIndeterminate(false);
		progress.setMinimum(0);
		progress.setMaximum(total);
	}

	@Override
	public void start() {
		progress.setIndeterminate(true);
	}

	@Override
	public void progress(int step) {
		progress.setValue(progress.getValue() + step);
	}

	@Override
	public void stop() {
		progress.setMaximum(0);
	}

}
