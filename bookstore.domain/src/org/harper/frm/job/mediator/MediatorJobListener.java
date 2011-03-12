package org.harper.frm.job.mediator;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MediatorJobListener implements PropertyChangeListener {

	private MediatorJob job;

	public MediatorJob getJob() {
		return job;
	}

	public void setJob(MediatorJob job) {
		this.job = job;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		job.setTriggerEvent(evt);
		job.call(null);
	}

}
