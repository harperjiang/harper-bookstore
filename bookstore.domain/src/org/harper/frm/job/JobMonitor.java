package org.harper.frm.job;

public interface JobMonitor {

	public void start(int total);
	
	public void start();
	
	public void progress(int step);
	
	public void stop();
}
