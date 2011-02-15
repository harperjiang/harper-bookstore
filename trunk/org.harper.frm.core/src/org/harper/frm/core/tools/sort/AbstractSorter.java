package org.harper.frm.core.tools.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.harper.frm.core.tools.TrivalComparator;


/**
 * 
 * @author Harper Jiang
 * 
 */
public abstract class AbstractSorter implements Sorter {

	@SuppressWarnings("unchecked")
	protected Comparator comparator;

	@SuppressWarnings("unchecked")
	protected int compare(Object a, Object b) {
		if (comparator == null)
			return ((Comparable<Object>) a).compareTo(b);
		return comparator.compare(a, b);
	}

	public synchronized <T extends Comparable<T>> List<T> sort(List<T> source) {
		return sort(source, new TrivalComparator<T>());
	}

	public synchronized <T> List<T> sort(List<T> source, String[] attribute,
			boolean[] ascending) {
		return sort(source, new MultiAttrComparator<T>(attribute, ascending));
	}

	protected boolean inplace = false;

	protected void setInplace(boolean inplace) {
		this.inplace = inplace;
	}

	public boolean isInplace() {
		return inplace;
	}

	@SuppressWarnings("unchecked")
	List array;

	protected <T> void wrap(List<T> input) {
		if (inplace) {
			array = input;
			return;
		}
		ArrayList<T> container = new ArrayList<T>();
		container.addAll(input);
		array = container;
	}
}
