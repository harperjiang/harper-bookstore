/*
 * author Harper Jiang
 * 
 * created in 2006-2-24 11:17:13
 */
package org.harper.frm.gui.swing.comp.textfield;

import java.math.BigDecimal;
import java.text.ParseException;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import org.harper.frm.gui.swing.manager.ComponentBinding;

public class NumTextField extends JFormattedTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4888423771866106670L;

	BigDecimal prevValue = null;

	public NumTextField() {
		super();
		this.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
		this.setFormatterFactory(new NumFormatterFactory());
	}

	public void setValue(Object val) {
		if (val instanceof BigDecimal)
			prevValue = (BigDecimal) val;
		super.setValue(val);
	}

	protected class NumFormatter extends JFormattedTextField.AbstractFormatter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7733479029734938405L;

		public Object stringToValue(String text) throws ParseException {
			try {
				prevValue = new BigDecimal(text);
			} catch (Exception e) {
			}
			return prevValue;
		}

		public String valueToString(Object value) throws ParseException {
			try {
				return ((BigDecimal) value).toPlainString();
			} catch (Exception e) {
				return null;
			}
		}
	}

	protected class NumFormatterFactory extends
			JFormattedTextField.AbstractFormatterFactory {

		public AbstractFormatter getFormatter(JFormattedTextField tf) {
			return new NumFormatter();
		}
	}

	public class NumTextBinding extends ComponentBinding {
		public NumTextBinding(String attribute) {
			super();
			setComponent(NumTextField.this);
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
				value =  NumTextField.this.getValue();
				firePropertyChange(oldValue, value);
			}

			public void insertUpdate(DocumentEvent e) {
				Object oldValue = value;
				value = NumTextField.this.getValue();
				firePropertyChange(oldValue, value);
			}

			public void removeUpdate(DocumentEvent e) {
				Object oldValue = value;
				value =  NumTextField.this.getValue();
				firePropertyChange(oldValue, value);
			}

		}
	}
}
