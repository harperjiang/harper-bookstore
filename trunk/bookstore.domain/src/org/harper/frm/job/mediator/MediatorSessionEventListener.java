package org.harper.frm.job.mediator;

import java.util.Enumeration;

import oracle.toplink.publicinterface.UnitOfWork;
import oracle.toplink.sessions.SessionEvent;
import oracle.toplink.sessions.SessionEventAdapter;

import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.service.TransactionContext;
import org.harper.frm.mediator.MediatorTransaction.Entry;
import org.harper.mediator.MediatorEvent;

public class MediatorSessionEventListener extends SessionEventAdapter {

	Class<? extends Entity> entityClass;

	public MediatorSessionEventListener(Class<? extends Entity> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public void preCommitUnitOfWork(SessionEvent event) {
		UnitOfWork uow = (UnitOfWork) event.getSession();
		Enumeration<Object> newe = uow.getNewObjectsCloneToOriginal().keys();
		while (newe.hasMoreElements()) {
			Object next = newe.nextElement();
			if (entityClass.isInstance(next)) {
				TransactionContext.getMediatorTransaction().addEvent(
						new Entry(entityClass.getName(),
								new MediatorEvent(next)));
				return;
			}
		}
		Enumeration<Object> dels = uow.getDeletedObjects().keys();
		while (dels.hasMoreElements()) {
			Object next = newe.nextElement();
			if (entityClass.isInstance(next)) {
				TransactionContext.getMediatorTransaction().addEvent(
						new Entry(entityClass.getName(),
								new MediatorEvent(next)));
				return;
			}
		}
		Enumeration<Object> updates = uow.getChanges()
				.getCloneToObjectChangeSet().keys();
		while (updates.hasMoreElements()) {
			Object next = newe.nextElement();
			if (entityClass.isInstance(next)) {
				TransactionContext.getMediatorTransaction().addEvent(
						new Entry(entityClass.getName(),
								new MediatorEvent(next)));
				return;
			}
		}
	}
}
