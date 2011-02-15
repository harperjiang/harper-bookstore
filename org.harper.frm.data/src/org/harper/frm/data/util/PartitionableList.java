package org.harper.frm.data.util;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.Validate;

/**
 * A basic implmentation of {@link IPartitionable} and {@link IPartition} using
 * List as inner container.
 * 
 * @author Harper Jiang
 * @version 1.0 2009-03-12
 * @since Component 1.1
 */
public class PartitionableList<T> extends ArrayList<T> implements IPartitionable<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 303109431020431910L;

	public PartitionableList() {
		super();
		splitlock = new AtomicInteger(-1);
	}

	@SuppressWarnings("unchecked")
	public List<T> getElements() {
		return (List<T>) clone();
	}

	public void setElements(List<T> elements) {
		this.clear();
		this.addAll(elements);
	}

	private AtomicInteger splitlock;

	public List<PartitionableList<T>> partition(int partition) {
		int cursize = size();
		if (!splitlock.compareAndSet(-1, cursize))
			throw new ConcurrentModificationException();
		Validate.isTrue(partition <= cursize);
		ArrayList<PartitionableList<T>> partitions = new ArrayList<PartitionableList<T>>();
		int left = cursize % partition;
		int count = cursize / partition;
		int pointer = 0;
		for (int i = 0; i < partition; i++) {
			PartitionableList<T> partitioni = new PartitionableList<T>();
			int nextlength = ((i >= left) ? count : (count + 1));
			partitioni.addAll(subList(pointer, pointer + nextlength));
			pointer += nextlength;
			partitions.add(partitioni);
		}

		splitlock.set(-1);
		return partitions;
	}
}
