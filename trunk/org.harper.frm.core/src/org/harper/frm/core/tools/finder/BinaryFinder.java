package org.harper.frm.core.tools.finder;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.harper.frm.core.tools.TrivalComparator;
import org.harper.frm.core.tools.sort.HeapSorter;



/**
 * 
 * @author Harper Jiang
 * 
 */
public class BinaryFinder implements Finder {

	@SuppressWarnings("unchecked")
	public <T> T find(List<T> source, boolean sorted, Comparator<T> comp, T inst) {
		Comparator<T> comparator = comp;

		if (comp == null) {
			if (inst instanceof Comparable) {
				comparator = (Comparator<T>) new TrivalComparator();
			} else {
				throw new IllegalArgumentException("Not comparable");
			}
		}
		if (!sorted)
			new HeapSorter(true).sort(source, comp);
		return subfind((sorted ? source : new HeapSorter().sort(source,
				comparator)), 0, -1, comparator, inst);
	}

	protected <T> T subfind(List<T> source, int start, int end,
			Comparator<T> comparator, T inst) {
		if (end == -1)
			end = source.size();
		Validate.isTrue(start <= end);
		if (start == end) {
			return comparator.compare(source.get(start), inst) == 0 ? source
					.get(start) : null;
		}
		int center = (end + start) / 2;

		int resu = comparator.compare(inst, source.get(center));
		if (resu == 0)
			return source.get(center);
		if (center == start || center == end)
			return null;
		return resu > 0 ? subfind(source, center, end, comparator, inst)
				: subfind(source, start, center, comparator, inst);
	}

}
