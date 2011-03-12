package org.harper.mediator;

import java.beans.PropertyChangeEvent;

public class MediatorEvent extends PropertyChangeEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7333953814945694643L;

	public MediatorEvent(Object source, String propertyName, Object oldValue,
			Object newValue) {
		super(source, propertyName, oldValue, newValue);
		setObjectLevel(true);
	}

	public MediatorEvent(Object source) {
		super(source, null, null, null);
		setObjectLevel(false);
	}

	private boolean objectLevel;

	public boolean isObjectLevel() {
		return objectLevel;
	}

	public void setObjectLevel(boolean objectLevel) {
		this.objectLevel = objectLevel;
	}

}
