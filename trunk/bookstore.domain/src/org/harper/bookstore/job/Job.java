package org.harper.bookstore.job;

public interface Job {

	public Object call(JobMonitor monitor);

}
