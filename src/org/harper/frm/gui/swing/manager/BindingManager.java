package org.harper.frm.gui.swing.manager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.harper.frm.core.IAdaptor;
import org.harper.frm.core.tools.bean.BeanAccess;
import org.harper.frm.gui.MiscUtils;
import org.springframework.util.CollectionUtils;

public class BindingManager implements PropertyChangeListener {

	private IAdaptor bean;

	private HashMap<String, Vector<IBinding>> bindingMap;

	private PropertyChangeSupport delegate;

	public BindingManager(IAdaptor bean) {
		this.bean = bean;
		delegate = new PropertyChangeSupport(this);
		PropertyChangeSupport pcs = null;
		if ((pcs = (PropertyChangeSupport) bean
				.getAdaptor(PropertyChangeSupport.class)) != null) {
			pcs.addPropertyChangeListener(this);
		}
		bindingMap = new HashMap<String, Vector<IBinding>>();
	}

	public void setBean(IAdaptor bean) {
		PropertyChangeSupport pcs = null;
		if ((pcs = (PropertyChangeSupport) this.bean
				.getAdaptor(PropertyChangeSupport.class)) != null) {
			pcs.removePropertyChangeListener(this);
		}
		this.bean = bean;
		if ((pcs = (PropertyChangeSupport) bean
				.getAdaptor(PropertyChangeSupport.class)) != null) {
			pcs.addPropertyChangeListener(this);
		}
		loadAll();
	}

	public void addBinding(IBinding binding) {
		binding.addPropertyChangeListener(this);
		if (bindingMap.get(binding.getAttribute()) == null)
			bindingMap.put(binding.getAttribute(), new Vector<IBinding>());
		bindingMap.get(binding.getAttribute()).add(binding);
	}

	public IAdaptor getBean() {
		return bean;
	}

	public void propertyChange(PropertyChangeEvent evt) {
		/*
		 * When the value of control changed, set corresponding value to the
		 * bean.
		 */
		try {
			if (IBinding.BINDING_VAL.equals(evt.getPropertyName())) {
				IBinding binding = (IBinding) evt.getSource();
				String attr = binding.getAttribute();
				Object value = evt.getNewValue();
				saveToBean(attr, value);
				return;
			}
			/*
			 * Value of the bean changed. Load value then set to corresponding
			 * controls.
			 */
			if (evt.getSource().equals(bean)) {
				List<IBinding> bindingList = new ArrayList<IBinding>();
				for (Entry<String, Vector<IBinding>> entry : bindingMap
						.entrySet()) {
					if (entry.getKey().startsWith(evt.getPropertyName()))
						bindingList.addAll(entry.getValue());
				}
				if (CollectionUtils.isEmpty(bindingList))
					return;
				for (Iterator bi = bindingList.iterator(); bi.hasNext();) {
					IBinding binding = (IBinding) bi.next();
					if (!MiscUtils.equal(binding.getValue(), evt.getNewValue())) {
						if (binding.getAttribute()
								.equals(evt.getPropertyName()))
							binding.setValue(evt.getNewValue());
						else {
							String child = binding.getAttribute();
							String parent = evt.getPropertyName();
							String attr = child.substring(parent.length() + 1);
							binding.setValue(null == evt.getNewValue() ? null
									: BeanAccess.getInstance().get(attr,
											evt.getNewValue()));
						}
					}
				}
			}
		} finally {
			firePropertyChange(evt);
		}
	}

	protected Object loadFromBean(String attribute) {

		try {
			return PropertyUtils.getProperty(getBean(), attribute);
		} catch (NestedNullException e) {
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void saveToBean(String attribute, Object value) {
		try {

			Object oldVal = PropertyUtils.getProperty(bean, attribute);
			if (!MiscUtils.equal(oldVal, value)) {
				// ((PropertyChangeSupport) bean
				// .getAdapter(PropertyChangeSupport.class))
				// .removePropertyChangeListener(this);
				PropertyUtils.setProperty(bean, attribute, value);
				// ((PropertyChangeSupport) bean
				// .getAdapter(PropertyChangeSupport.class))
				// .addPropertyChangeListener(this);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Load all attribtues from bean to ui.
	 * 
	 */
	public void loadAll() {
		for (Iterator ite = bindingMap.entrySet().iterator(); ite.hasNext();) {
			Vector<IBinding> bindings = (Vector<IBinding>) ((Map.Entry) ite
					.next()).getValue();
			for (Iterator bi = bindings.iterator(); bi.hasNext();) {
				IBinding binding = (IBinding) bi.next();
				binding.setValue(loadFromBean(binding.getAttribute()));
			}
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		delegate.addPropertyChangeListener(listener);
	}

	public void firePropertyChange(PropertyChangeEvent evt) {
		delegate.firePropertyChange(evt);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		delegate.removePropertyChangeListener(listener);
	}

}
