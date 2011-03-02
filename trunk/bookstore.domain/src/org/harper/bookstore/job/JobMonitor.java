package org.harper.bookstore.job;

public interface JobMonitor {

	public void start(int total);
	
	public void start();
	
	public void progress(int step);
	
	public void stop();
}
