package org.harper.frm.data.formatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.harper.frm.data.IFile;
import org.springframework.util.CollectionUtils;


/**
 * Create a formatter to delegate its job to a chain of formatters
 * 
 * @author Harper Jiang
 * @since com.oocl.frm.data 1.0
 * @version 1.0 Feb 1, 2010
 */
public class FormatterChain extends AbstractFormatter {

    private List<AbstractFormatter> formatters;

    public FormatterChain(AbstractFormatter... fmts) {
        super();
        this.formatters = new ArrayList<AbstractFormatter>();
        for (AbstractFormatter formatter : fmts) {
            if (null != formatter) {
                formatters.add(formatter);
            }
        }
    }

    @Override
    public void setRowLimit(long rowLimit) {
        for (AbstractFormatter formatter : formatters) {
            formatter.setRowLimit(rowLimit);
        }
    }

    public IFile format(Object input) throws IOException,
            UnsupportedOperationException {

        if (CollectionUtils.isEmpty(formatters))
            throw new IllegalStateException("No Formatter Presents");
        Object current = input;

        for (AbstractFormatter formatter : formatters) {
            current = formatter.format(current);
        }

        return (IFile) current;
    }

}
