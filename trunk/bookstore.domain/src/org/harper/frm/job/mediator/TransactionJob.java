package org.harper.frm.job.mediator;

import org.harper.frm.job.Job;
import org.harper.frm.job.JobMonitor;
import org.harper.frm.transaction.Transaction;

public abstract class TransactionJob implements Job {

	private boolean readOnly;

	public TransactionJob(boolean read) {
		this.readOnly = read;
	}

	@Override
	public Object call(JobMonitor monitor) {
		Transaction.start();
		try {
			Object result = executeInTransaction(monitor);
			if (!readOnly)
				Transaction.commit();
			return result;
		} catch (Exception e) {
			Transaction.release();
			if (e instanceof RuntimeException)
				throw (RuntimeException) e;
			throw new RuntimeException(e);
		}
	}

	protected abstract Object executeInTransaction(JobMonitor monitor);
}
