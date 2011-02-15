package org.harper.frm.gui.swing.manager;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class JLabelBinding extends ComponentBinding {

	public JLabelBinding(JLabel label, String attribute) {
		super();
		setComponent(label);
		setAttribute(attribute);
	}

	@Override
	public void setComponent(JComponent component) {
		super.setComponent(component);
	}

	@Override
	public void setValue(Object value) {
		super.setValue(value);
		JLabel label = (JLabel) getComponent();
		if (getValue() != null)
			label.setText(String.valueOf(getValue()));
	}

}
