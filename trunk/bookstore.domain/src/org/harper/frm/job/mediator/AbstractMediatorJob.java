package org.harper.frm.job.mediator;

import java.beans.PropertyChangeEvent;

public abstract class AbstractMediatorJob extends TransactionJob implements
		MediatorJob {

	public AbstractMediatorJob(boolean read) {
		super(read);
	}

	private PropertyChangeEvent triggerEvent;

	public PropertyChangeEvent getTriggerEvent() {
		return triggerEvent;
	}

	public void setTriggerEvent(PropertyChangeEvent triggerEvent) {
		this.triggerEvent = triggerEvent;
	}

}
