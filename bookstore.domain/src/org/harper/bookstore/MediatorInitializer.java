package org.harper.bookstore;

import org.harper.bookstore.domain.store.StoreEntry;
import org.harper.bookstore.job.MonitorStockJob;
import org.harper.frm.job.mediator.MediatorJobListener;
import org.harper.frm.job.mediator.MediatorSessionEventListener;
import org.harper.frm.toplink.SessionManager;
import org.harper.mediator.MediatorManager;

public class MediatorInitializer {

	public static void init() {
		initReceiver();
		initSender();
	}

	protected static void initSender() {
		SessionManager
				.getInstance()
				.getSession()
				.getEventManager()
				.addListener(new MediatorSessionEventListener(StoreEntry.class));
	}

	protected static void initReceiver() {
		MediatorJobListener stockMonitorListener = new MediatorJobListener(
				new MonitorStockJob());
		MediatorManager.getInstance().addListener(StoreEntry.class.getName(),
				stockMonitorListener);
	}
}
