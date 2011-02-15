/*
 * author Harper Jiang
 * 
 * created in 2006-2-7 14:08:55
 */
package org.harper.frm.gui.swing.comp.textfield;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class UpperCaseTextField extends JTextField {
    
    public UpperCaseTextField() {
        super();
    }
    
    public UpperCaseTextField(int cols) {
        super(cols);
    }

    protected Document createDefaultModel() {
        return new UpperCaseDocument();
    }

    public static class UpperCaseDocument extends PlainDocument {

        public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {

            if (str == null) {
                return;
            }
            char[] upper = str.toCharArray();
            for (int i = 0; i < upper.length; i++) {
                upper[i] = Character.toUpperCase(upper[i]);
            }
            super.insertString(offs, new String(upper), a);
        }

    }
}
