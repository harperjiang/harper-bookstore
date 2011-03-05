package org.harper.frm.mediator;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.harper.mediator.MediatorManager;

public class MediatorTransaction {

	public static class Entry {

		private String ns;

		private PropertyChangeEvent event;

		public Entry(String ns, PropertyChangeEvent evt) {
			this.ns = ns;
			this.event = evt;
		}

		public String getNs() {
			return ns;
		}

		public PropertyChangeEvent getEvent() {
			return event;
		}

	}

	private List<Entry> events;

	public MediatorTransaction() {
		this.events = new ArrayList<Entry>();
	}

	public void addEvent(Entry event) {
		events.add(event);
	}

	public void commit() {
		for (Entry event : events)
			MediatorManager.getInstance().sendMessage(event.getNs(),
					event.getEvent());
	}
}
