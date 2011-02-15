package org.harper.frm.data.formatter;

import java.util.EventListener;

/**
 * 
 * @author Harper Jiang
 * @since ips.frm.component 1.0
 * @version 1.0 Jun 15, 2009
 */
public interface FormatListener extends EventListener {

	/**
	 * 
	 * @param event
	 */
	public void onFormatBegin(FormatEvent event);

	/**
	 * 
	 * @param event
	 */
	public void onSectionBegin(FormatEvent event);

	/**
	 * 
	 * @param event
	 */
	public void onSectionEnd(FormatEvent event);

	/**
	 * 
	 * @param event
	 */
	public void onPageBegin(FormatEvent event);

	/**
	 * 
	 * @param event
	 */
	public void onPageEnd(FormatEvent event);

	/**
	 * 
	 * @param event
	 */
	public void onFormatEnd(FormatEvent event);
}
