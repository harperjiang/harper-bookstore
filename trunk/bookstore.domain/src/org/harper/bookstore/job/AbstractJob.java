package org.harper.bookstore.job;

import oracle.toplink.sessions.UnitOfWork;

import org.harper.bookstore.service.TransactionContext;
import org.harper.frm.core.logging.LogManager;
import org.harper.frm.toplink.SessionManager;


public abstract class AbstractJob implements Job {

	private boolean readonly;

	@Override
	public void run() {
		startTransaction();
		try {
			execute();
			if (readonly)
				releaseTransaction();
			else
				commitTransaction();
		} catch (Exception e) {
			LogManager.getInstance().getLogger(getClass())
					.error("Exception", e);
			releaseTransaction();
		}
	}

	protected abstract void execute();

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
