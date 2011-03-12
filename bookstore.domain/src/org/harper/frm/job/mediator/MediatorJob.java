package org.harper.frm.job.mediator;

import java.beans.PropertyChangeEvent;

import org.harper.frm.job.Job;

public interface MediatorJob extends Job {

	public PropertyChangeEvent getTriggerEvent();

	public void setTriggerEvent(PropertyChangeEvent triggerEvent);

}
