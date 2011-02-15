package org.harper.frm.data.formatter;

import java.util.Iterator;

/**
 * Interface of content provider that output table like datasets.
 * 
 * @author Harper Jiang
 * @since ips.frm.component 1.0
 * @version 1.0 Jun 15, 2009
 */
public interface ITableContentProvider<T> {

	/**
	 * 
	 * @param input
	 * @return
	 */
	public Iterator<T> iterator(Object input);

	/**
	 * 
	 * @return
	 */
	public String[] getColumnNames(Object input);

	/**
	 * 
	 * @return
	 */
	public int getColumnCount(Object input);

	/**
	 * 
	 * @param rowInput
	 * @param column
	 * @return
	 */
	public Object get(Object rowInput, int column);
}
