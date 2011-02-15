package org.harper.frm.data.formatter;

import javax.swing.event.EventListenerList;

import org.harper.frm.data.IFileDescriber;


/**
 * 
 * @author Harper Jiang
 * @since ips.frm.component 1.0
 * @version 1.0 Jun 15, 2009
 */
public abstract class AbstractFormatter implements IFormatter {

	public AbstractFormatter() {
		super();
		delegate = new EventListenerList();
	}
	

	public static final long NO_LIMIT = -1;
    

    private long rowLimit = NO_LIMIT;

    public long getRowLimit() {
        return rowLimit;
    }

    public void setRowLimit(long rowLimit) {
        this.rowLimit = rowLimit;
    }

	private IFileDescriber describer;

	public void setDescriber(IFileDescriber describer) {
		this.describer = describer;
	}

	public IFileDescriber getDescriber() {
		return describer;
	}

	private EventListenerList delegate;

	public void addPageListener(FormatListener listener) {
		delegate.add(FormatListener.class, listener);
	}

	public void removePageListener(FormatListener listener) {
		delegate.remove(FormatListener.class, listener);
	}

	/*
	 * Formatting begin notification
	 */
	protected void fireBegin() {
		FormatEvent event = null;
		FormatListener[] listeners = delegate
				.getListeners(FormatListener.class);
		for (int i = 0; i < listeners.length; i++) {
			if (null == event)
				event = new FormatEvent(this);
			listeners[i].onFormatBegin(event);
		}
	}

	/*
	 * Formatting end notification
	 */
	protected void fireEnd() {
		FormatEvent event = null;
		FormatListener[] listeners = delegate
				.getListeners(FormatListener.class);
		for (int i = 0; i < listeners.length; i++) {
			if (null == event)
				event = new FormatEvent(this);
			listeners[i].onFormatEnd(event);
		}
	}

	/*
	 * Formatting paging notification
	 */
	protected void firePageEnd(int page) {
		FormatEvent event = null;
		FormatListener[] listeners = delegate
				.getListeners(FormatListener.class);
		for (int i = 0; i < listeners.length; i++) {
			if (null == event)
				event = new FormatEvent(this);
			listeners[i].onPageEnd(event);
		}
	}

	/*
	 * Formatting paging notification
	 */
	protected void firePageBegin(int page) {
		FormatEvent event = null;
		FormatListener[] listeners = delegate
				.getListeners(FormatListener.class);
		for (int i = 0; i < listeners.length; i++) {
			if (null == event)
				event = new FormatEvent(this);
			listeners[i].onPageBegin(event);
		}
	}

	/*
	 * Formatting paging notification
	 */
	protected void fireSectionEnd(int section) {
		FormatEvent event = null;
		FormatListener[] listeners = delegate
				.getListeners(FormatListener.class);
		for (int i = 0; i < listeners.length; i++) {
			if (null == event)
				event = new FormatEvent(this);
			listeners[i].onSectionEnd(event);
		}
	}

	/*
	 * Formatting paging notification
	 */
	protected void fireSectionBegin(int section) {
		FormatEvent event = null;
		FormatListener[] listeners = delegate
				.getListeners(FormatListener.class);
		for (int i = 0; i < listeners.length; i++) {
			if (null == event)
				event = new FormatEvent(this);
			listeners[i].onSectionBegin(event);
		}
	}

}
