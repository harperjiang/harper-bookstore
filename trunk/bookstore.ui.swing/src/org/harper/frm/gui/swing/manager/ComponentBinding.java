package org.harper.frm.gui.swing.manager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JComponent;

public abstract class ComponentBinding implements IBinding {

	private JComponent component;

	private String attribute;

	protected Object value;

	private PropertyChangeSupport delegate;

	public ComponentBinding() {
		super();
		delegate = new PropertyChangeSupport(this);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		delegate.addPropertyChangeListener(listener);
	}

	public void firePropertyChange(Object oldValue,Object newValue) {
		PropertyChangeEvent evt = new PropertyChangeEvent(this,
				IBinding.BINDING_VAL, oldValue, newValue);

		delegate.firePropertyChange(evt);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		delegate.removePropertyChangeListener(listener);
	}

	public JComponent getComponent() {
		return component;
	}

	/**
	 * Subclasses should override this class to implements listener
	 * registratinon on control.
	 * 
	 * @param control
	 */
	public void setComponent(JComponent component) {
		this.component = component;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
