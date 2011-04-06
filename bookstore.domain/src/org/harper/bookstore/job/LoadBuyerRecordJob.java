package org.harper.bookstore.job;

import org.harper.frm.job.AbstractJob;
import org.harper.frm.job.JobMonitor;

public class LoadBuyerRecordJob extends AbstractJob {

	@Override
	protected Object execute(JobMonitor monitor) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		LoadChatRecordJob job = new LoadChatRecordJob();
		job.call(null);
	}
}
