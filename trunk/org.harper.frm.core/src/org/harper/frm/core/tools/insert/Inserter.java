package org.harper.frm.core.tools.insert;

import java.util.Comparator;
import java.util.List;

/**
 * An inserter assume the underling list is already sorted and try to insert an
 * element into a proper position and keep the list sorted.
 * 
 * @author Harper Jiang
 * @since Common 1.0
 * @version 1.1 - Support to generic type
 */
public interface Inserter {
	/**
	 * Insert an object into a proper position of the given list. Use the
	 * object's {@link Comparable#compareTo(Object)} method.
	 * 
	 * @param <T>
	 * @param inst
	 * @param list
	 */
	public <T extends Comparable<T>> void insert(T inst, List<T> list);

	/**
	 * 
	 * @param <T>
	 * @param inst
	 *            The instance to be inserted.
	 * @param list
	 *            The list that this element will be inserted in.
	 * @param isSet
	 *            Whether the given list should be kept as a set. If this
	 *            parameter was set as true, the elements will not be inserted
	 *            if there already exists one element that equals to it in the
	 *            list.
	 */
	public <T> void insert(T inst, List<T> list, boolean isSet,
			Comparator<T> comp);
}
