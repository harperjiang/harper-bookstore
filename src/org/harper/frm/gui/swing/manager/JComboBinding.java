package org.harper.frm.gui.swing.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;

public class JComboBinding extends ComponentBinding {

	public JComboBinding(JComboBox combo, String attribute) {
		super();
		setComponent(combo);
		setAttribute(attribute);
	}

	private ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object oldValue = value;
			value = ((JComboBox) getComponent()).getSelectedItem();
			firePropertyChange(oldValue, value);
		}
	};

	@Override
	public void setComponent(JComponent component) {
		if (!(component instanceof JComboBox))
			throw new IllegalArgumentException();
		((JComboBox) component).addActionListener(listener);
		super.setComponent(component);
	}

	@Override
	public void setValue(Object value) {
		super.setValue(value);
		JComboBox combo = (JComboBox) getComponent();
		combo.removeActionListener(listener);
		if (getValue() != null)
			combo.setSelectedItem(getValue());
		combo.addActionListener(listener);
	}
}
