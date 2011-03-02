package org.harper.bookstore.job;

import oracle.toplink.sessions.UnitOfWork;

import org.harper.bookstore.service.TransactionContext;
import org.harper.frm.core.logging.LogManager;
import org.harper.frm.toplink.SessionManager;

public abstract class AbstractJob implements Job {

	private boolean readonly;

	public Object call() {
		return call(null);
	}

	@Override
	public Object call(JobMonitor monitor) {
		startTransaction();
		try {
			Object result = execute(monitor);
			if (readonly)
				releaseTransaction();
			else
				commitTransaction();
			return result;
		} catch (Exception e) {
			LogManager.getInstance().getLogger(getClass())
					.error("Exception", e);
			releaseTransaction();
			throw new RuntimeException(e);
		}
	}

	protected abstract Object execute(JobMonitor monitor);

	public void startTransaction() {
		UnitOfWork uow = SessionManager.getInstance().getSession()
				.acquireUnitOfWork();
		TransactionContext.get().push(uow);
	}

	public void commitTransaction() {
		((UnitOfWork) TransactionContext.get().pop()).commit();
	}

	public void releaseTransaction() {
		((UnitOfWork) TransactionContext.get().pop()).release();
	}
}
