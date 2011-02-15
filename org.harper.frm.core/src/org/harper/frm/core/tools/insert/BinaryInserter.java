package org.harper.frm.core.tools.insert;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.harper.frm.core.tools.TrivalComparator;


/**
 * An implementation that try to find a proper position in the list using binary
 * search.
 * 
 * @author Harper Jiang
 * @since Common 1.0
 * @version 1.1 - Support to generic type
 */
public class BinaryInserter implements Inserter {

	public <T extends Comparable<T>> void insert(T inst, List<T> list) {
		insert(inst, list, true, null);
	}

	public <T> void insert(T inst, List<T> list, boolean isSet,
			Comparator<T> comp) {
		Comparator<T> comparator = comp;

		subins(list, 0, list.size() - 1, comparator, inst, isSet);
	}

	@SuppressWarnings("unchecked")
	protected <T> void subins(List<T> list, int start, int end,
			Comparator<T> comparator, T inst, boolean isSet) {
		if (list.size() == 0) {
			list.add(inst);
			return;
		}
		Validate.isTrue(end < list.size());
		if (comparator == null)
			comparator = (Comparator<T>) new TrivalComparator();
		if (comparator.compare(inst, list.get(start)) <= 0) {
			list.add(start, inst);
			return;
		}
		if (comparator.compare(inst, list.get(end)) >= 0) {
			list.add(inst);
			return;
		}
		int center = (start + end) / 2;
		if (center == start || center == end) {
			list.add(end, inst);
			return;
		}
		int result = comparator.compare(inst, list.get(center));
		if (result == 0) {
			if (!isSet)
				list.add(center, inst);
			return;
		}
		if (result > 0 && (comparator.compare(inst, list.get(end)) <= 0))
			subins(list, center, end, comparator, inst, isSet);
		else
			subins(list, 0, center, comparator, inst, isSet);

	}
}
