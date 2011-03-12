package org.harper.frm.job.mediator;

import java.beans.PropertyChangeEvent;

import org.harper.frm.job.Job;

public abstract class MediatorJob implements Job {
	
	private PropertyChangeEvent triggerEvent;

	public PropertyChangeEvent getTriggerEvent() {
		return triggerEvent;
	}

	public void setTriggerEvent(PropertyChangeEvent triggerEvent) {
		this.triggerEvent = triggerEvent;
	}
}
