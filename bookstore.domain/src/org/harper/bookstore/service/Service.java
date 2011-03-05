package org.harper.bookstore.service;

import oracle.toplink.sessions.UnitOfWork;

import org.harper.bookstore.repo.RepoFactory;
import org.harper.frm.mediator.MediatorTransaction;
import org.harper.frm.toplink.SessionManager;

public class Service {

	public RepoFactory getRepoFactory() {
		return RepoFactory.INSTANCE;
	}

	public void startTransaction() {
		UnitOfWork uow = SessionManager.getInstance().getSession()
				.acquireUnitOfWork();
		TransactionContext.get().push(uow);
		TransactionContext.getMT().push(new MediatorTransaction());
	}

	public void commitTransaction() {
		((UnitOfWork) TransactionContext.get().pop()).commit();
		TransactionContext.getMT().pop().commit();
	}

	public void releaseTransaction() {
		if (!TransactionContext.get().isEmpty())
			TransactionContext.get().pop().release();
		if(!TransactionContext.getMT().isEmpty())
			TransactionContext.getMT().pop();
	}
}
