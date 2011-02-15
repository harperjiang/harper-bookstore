package org.harper.frm.data.util;

import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * Object that implements <code>IPartitionable</code> is a container of elements
 * with type T that can be split into {@link IPartitionable#split(int) pieces}
 * by invoking {@link #split(int)} method.
 * 
 * @author Harper Jiang
 * @version 1.0 2009-03-12
 * @since Component 1.1
 * 
 * @param <T>
 *            Elements inside the container
 */
public interface IPartitionable<T> {

	/**
	 * The total count of elements. Also decides the max available partiton
	 * count.
	 * 
	 * @return the count of elements.
	 */
	public int size();

	/**
	 * Get the elements inside this container
	 * 
	 * @return list of elements.
	 */
	public List<T> getElements();

	/**
	 * Split the object into partitions. This methods return an array of
	 * IPartitionable, which also means that it can be split again and again
	 * untill no more than one elements in each partition.
	 * 
	 * @param count
	 *            how many partition to split
	 * @return split partition
	 * @throws IllegalArgument
	 *             If partition > {@link #size()}
	 * @throws ConcurrentModificationException
	 *             If the same instance was invoked by two threads
	 *             simutaneously.
	 */
	public List<? extends IPartitionable<T>> partition(int count);

}
