package org.harper.frm.gui.swing.manager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.apache.commons.beanutils.PropertyUtils;
import org.harper.frm.core.IAdaptor;
import org.harper.frm.gui.MiscUtils;


public abstract class AbstractBean implements IAdaptor, PropertyChangeListener {

	private PropertyChangeSupport delegate;

	public AbstractBean() {
		delegate = new PropertyChangeSupport(this);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		delegate.addPropertyChangeListener(listener);
	}

	public void firePropertyChange(PropertyChangeEvent evt) {
		delegate.firePropertyChange(evt);
	}

	public void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		delegate.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		delegate.removePropertyChangeListener(listener);
	}

	public void propertyChange(PropertyChangeEvent evt) {

	}

	public Object getAdaptor(Class adapterClass) {
		if (PropertyChangeListener.class.equals(adapterClass))
			return this;
		if (PropertyChangeSupport.class.equals(adapterClass))
			return delegate;
		return null;
	}

	protected void setValue(String attr, Object value) {

		try {
			Object oldValue = PropertyUtils.getProperty(this, attr);

			if (MiscUtils.equal(oldValue, value))
				return;
			PropertyUtils.setProperty(this, attr, value);
			firePropertyChange(attr, oldValue, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
