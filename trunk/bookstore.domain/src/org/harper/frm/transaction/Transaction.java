package org.harper.frm.transaction;

import oracle.toplink.sessions.UnitOfWork;

import org.harper.bookstore.service.TransactionContext;
import org.harper.frm.mediator.MediatorTransaction;
import org.harper.frm.toplink.SessionManager;

public class Transaction {

	public static void start() {
		UnitOfWork uow = SessionManager.getInstance().getSession()
				.acquireUnitOfWork();
		TransactionContext.get().push(uow);
		TransactionContext.getMT().push(new MediatorTransaction());
	}

	public static void commit() {
		((UnitOfWork) TransactionContext.get().pop()).commit();
		TransactionContext.getMT().pop().commit();
	}

	public static void release() {
		if (!TransactionContext.get().isEmpty())
			TransactionContext.get().pop().release();
		if (!TransactionContext.getMT().isEmpty())
			TransactionContext.getMT().pop();
	}
}
