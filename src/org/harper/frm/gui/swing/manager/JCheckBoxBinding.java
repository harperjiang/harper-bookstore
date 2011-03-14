package org.harper.frm.gui.swing.manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;

public class JCheckBoxBinding extends ComponentBinding {
	public JCheckBoxBinding(JCheckBox check, String attribute) {
		super();
		setComponent(check);
		setAttribute(attribute);
	}

	private ActionListener listener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object oldValue = value;
			value = ((JCheckBox) getComponent()).isSelected();
			firePropertyChange(oldValue, value);
		}
	};

	@Override
	public void setComponent(JComponent component) {
		if (!(component instanceof JCheckBox))
			throw new IllegalArgumentException();
		((JCheckBox) component).addActionListener(listener);
		super.setComponent(component);
	}

	@Override
	public void setValue(Object value) {
		super.setValue(value);
		JCheckBox check = (JCheckBox) getComponent();
		check.removeActionListener(listener);
		if (getValue() != null)
			check.setSelected((Boolean) getValue());
		check.addActionListener(listener);
	}
	
}
