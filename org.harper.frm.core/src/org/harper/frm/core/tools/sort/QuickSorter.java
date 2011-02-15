package org.harper.frm.core.tools.sort;

import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author Harper Jiang
 *
 */
public class QuickSorter extends AbstractSorter {
	
	public QuickSorter() {
		super();
	}
	
	public QuickSorter(boolean inplace) {
		this();
		this.setInplace(inplace);
	}
	
	private int partition(int start, int end) {
		Object x = array.get(end);
		int i = start - 1;
		for (int j = start; j < end; j++) {
			if (compare(array.get(j), x) < 0) {
				i++;
				SorterUtil.exchange(array, i, j);
			}
		}
		SorterUtil.exchange(array, i + 1, end);
		return i + 1;
	}

	private void quickSort(int start, int end) {
		if (start < end) {
			int partition = partition(start, end);
			quickSort(start, partition - 1);
			quickSort(partition + 1, end);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T>List<T> sort(List<T> source, Comparator<T> comparator) {
		this.comparator = comparator;
		wrap(source);
		quickSort(0, array.size() - 1);
		return array;
	}
}
