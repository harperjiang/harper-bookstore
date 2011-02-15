package org.harper.frm.core.tools.sort;

import java.lang.reflect.Array;
import java.util.Comparator;

public class ArrayComparator implements Comparator<Object[]> {

	private int[] indices;

	private boolean[] asc;

	public ArrayComparator(int[] indices, boolean[] ascending) {
		this.indices = indices;
		this.asc = ascending;
	}

	@SuppressWarnings("unchecked")
	public int compare(Object[] a, Object[] b) {
		try {
			for (int i = 0; i < indices.length; i++) {
				Comparable at = (Comparable) Array.get(a, indices[i]);
				Comparable bt = (Comparable) Array.get(b, indices[i]);
				if (at.compareTo(bt) == 0)
					continue;
				if (!(at.compareTo(bt) > 0 ^ asc[i]))
					return 1;
				else
					return -1;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return 0;
	}

}
