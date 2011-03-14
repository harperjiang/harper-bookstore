/*
 * author Harper Jiang
 * 
 * created in 2006-2-23 15:54:58
 */
package org.harper.frm.gui.swing.comp.textfield;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class LimitCountTextField extends JTextField {

    private int charLimit = 255;

    public LimitCountTextField() {
        super();
    }

    public LimitCountTextField(int columns) {
        super(columns);
        charLimit = columns;
    }

    protected Document createDefaultModel() {
        return new LimitCountDocument();
    }

    protected class LimitCountDocument extends PlainDocument {

        public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {
            if (str == null || offs >= charLimit) {
                return;
            }
            super.insertString(offs, str.substring(0, str.length() > charLimit
                    - offs ? (charLimit - offs) : str.length()), a);
        }
    }

}
