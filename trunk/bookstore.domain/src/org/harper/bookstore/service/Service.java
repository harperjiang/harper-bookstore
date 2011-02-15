package org.harper.bookstore.service;

import oracle.toplink.sessions.UnitOfWork;

import org.harper.bookstore.repo.RepoFactory;
import org.harper.frm.toplink.SessionManager;

public class Service {

	public RepoFactory getRepoFactory() {
		return RepoFactory.INSTANCE;
	}

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
