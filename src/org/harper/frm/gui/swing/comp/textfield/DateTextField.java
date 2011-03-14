/*
 * author Harper Jiang
 * 
 * created in 2006-2-24 11:17:13
 */
package org.harper.frm.gui.swing.comp.textfield;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import org.harper.frm.gui.swing.manager.ComponentBinding;

public class DateTextField extends JFormattedTextField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4888423771866106670L;

	DateFormat formatter = null;

	Date prevValue = null;

	public DateTextField(DateFormat format) {
		super();
		this.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
		formatter = format;
		this.setFormatterFactory(new DateFormatterFactory());
	}

	public void setValue(Object val) {
		if (val instanceof Date)
			prevValue = (Date) val;
		super.setValue(val);
	}

	protected class DateFormatter extends JFormattedTextField.AbstractFormatter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7733479029734938405L;

		public Object stringToValue(String text) throws ParseException {
			try {
				prevValue = formatter.parse(text,new ParsePosition(0));
			} catch (Exception e) {
			}
			return prevValue;
		}

		public String valueToString(Object value) throws ParseException {
			try {
				if (value instanceof java.util.Date)
					return formatter.format((Date) value);
				if (value instanceof java.util.Calendar)
					return formatter.format(((Calendar) value).getTime());
				return null;
			} catch (Exception e) {
				return null;
			}
		}
	}

	protected class DateFormatterFactory extends
			JFormattedTextField.AbstractFormatterFactory {

		public AbstractFormatter getFormatter(JFormattedTextField tf) {
			return new DateFormatter();
		}
	}

	public class DateTextBinding extends ComponentBinding {
		public DateTextBinding(String attribute) {
			super();
			setComponent(DateTextField.this);
			setAttribute(attribute);
		}

		private DocumentListener docListener = new DocumentAdapter();
		@Override
		public void setComponent(JComponent component) {
			if (!(component instanceof JTextComponent))
				throw new IllegalArgumentException();
			getDocument().addDocumentListener(
					docListener);
			super.setComponent(component);
		}

		@Override
		public void setValue(Object value) {
			super.setValue(value);
			getDocument().removeDocumentListener(docListener);
			if (getValue() != null)
				setText(formatter.format((Date)getValue()));
			getDocument().addDocumentListener(docListener);
		}
	
		private class DocumentAdapter implements DocumentListener {

			public void changedUpdate(DocumentEvent e) {
				Object oldValue = value;
				value = DateTextField.this.getValue();
				firePropertyChange(oldValue, value);
			}

			public void insertUpdate(DocumentEvent e) {
				Object oldValue = value;
				value = DateTextField.this.getValue();
				firePropertyChange(oldValue, value);
			}

			public void removeUpdate(DocumentEvent e) {
				Object oldValue = value;
				value = DateTextField.this.getValue();
				firePropertyChange(oldValue, value);
			}

		}
	}
}
