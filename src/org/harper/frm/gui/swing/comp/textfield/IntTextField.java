/*
 * author Harper Jiang
 * 
 * created in 2006-2-24 11:17:13
 */
package org.harper.frm.gui.swing.comp.textfield;

import java.text.ParseException;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import org.harper.frm.gui.swing.manager.ComponentBinding;

public class IntTextField extends JFormattedTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4888423771866106670L;

	Integer prevValue = null;

	public IntTextField() {
		super();
		this.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
		this.setFormatterFactory(new IntFormatterFactory());
	}

	public void setValue(Object val) {
		if (val instanceof Integer)
			prevValue = (Integer) val;
		super.setValue(val);
	}

	protected class IntFormatter extends JFormattedTextField.AbstractFormatter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7733479029734938405L;

		public Object stringToValue(String text) throws ParseException {
			try {
				prevValue = Integer.parseInt(text);
			} catch (Exception e) {
			}
			return prevValue;
		}

		public String valueToString(Object value) throws ParseException {
			try {
				return String.valueOf(value);
			} catch (Exception e) {
				return null;
			}
		}
	}

	protected class IntFormatterFactory extends
			JFormattedTextField.AbstractFormatterFactory {

		public AbstractFormatter getFormatter(JFormattedTextField tf) {
			return new IntFormatter();
		}
	}

	public class NumTextBinding extends ComponentBinding {
		public NumTextBinding(String attribute) {
			super();
			setComponent(IntTextField.this);
			setAttribute(attribute);
		}

		private DocumentListener docListener = new DocumentAdapter();

		@Override
		public void setComponent(JComponent component) {
			if (!(component instanceof JTextComponent))
				throw new IllegalArgumentException();
			getDocument().addDocumentListener(docListener);
			super.setComponent(component);
		}

		@Override
		public void setValue(Object value) {
			super.setValue(value);
			getDocument().removeDocumentListener(docListener);
			if (getValue() != null)
				setText(String.valueOf(getValue()));
			getDocument().addDocumentListener(docListener);
		}

		private class DocumentAdapter implements DocumentListener {

			public void changedUpdate(DocumentEvent e) {
				Object oldValue = value;
				value = IntTextField.this.getValue();
				if (null == value)
					value = Integer.valueOf(0);
				firePropertyChange(oldValue, value);
			}

			public void insertUpdate(DocumentEvent e) {
				Object oldValue = value;
				value = IntTextField.this.getValue();
				if (null == value)
					value = Integer.valueOf(0);
				firePropertyChange(oldValue, value);
			}

			public void removeUpdate(DocumentEvent e) {
				Object oldValue = value;
				value = IntTextField.this.getValue();
				if (null == value)
					value = Integer.valueOf(0);
				firePropertyChange(oldValue, value);
			}

		}
	}
}
