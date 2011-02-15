package org.harper.frm.core.tools.finder;

import java.util.Comparator;
import java.util.List;

/**
 * A <code>Finder</code> can find out an instance through an sorted list
 * efficiently.
 * 
 * @author Harper Jiang
 * @since Common 1.0
 * @version 1.1 - Support to generic type
 */
public interface Finder {
	/**
	 * Find out and instance that equals to the given instance out of the given
	 * list.
	 * 
	 * @param <T>
	 *            Type parameter
	 * @param source
	 *            The given list of target element.
	 * @param sorted
	 *            whenther the given list has been sorted.
	 * @param comp
	 *            Comparator used to compare the instances, null means use
	 *            {@link #equals(Object)}
	 * @param inst
	 *            Given instance used as indicator.
	 * @return The instance equals to the given one and resides in the list, or
	 *         null if nothing found.
	 */
	public <T> T find(List<T> source, boolean sorted, Comparator<T> comp, T inst);
}
