package org.harper.frm.gui.swing.manager;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

public class JTextBinding extends ComponentBinding {

	public JTextBinding(JTextComponent text, String attribute) {
		super();
		setComponent(text);
		setAttribute(attribute);
	}

	private DocumentListener docListener = new DocumentAdapter();

	@Override
	public void setComponent(JComponent component) {
		if (!(component instanceof JTextComponent))
			throw new IllegalArgumentException();
		((JTextComponent) component).getDocument().addDocumentListener(
				docListener);
		super.setComponent(component);
	}

	@Override
	public void setValue(Object value) {
		super.setValue(value);
		final JTextComponent textComp = (JTextComponent) getComponent();
		textComp.getDocument().removeDocumentListener(docListener);

		if (getValue() != null)
			textComp.setText(String.valueOf(getValue()));

		textComp.getDocument().addDocumentListener(docListener);
	}

	private class DocumentAdapter implements DocumentListener {

		public void changedUpdate(DocumentEvent e) {
			Object oldValue = value;
			value = ((JTextComponent) getComponent()).getText();
			firePropertyChange(oldValue, value);
		}

		public void insertUpdate(DocumentEvent e) {
			Object oldValue = value;
			value = ((JTextComponent) getComponent()).getText();
			firePropertyChange(oldValue, value);
		}

		public void removeUpdate(DocumentEvent e) {
			Object oldValue = value;
			value = ((JTextComponent) getComponent()).getText();
			firePropertyChange(oldValue, value);
		}

	}
}
