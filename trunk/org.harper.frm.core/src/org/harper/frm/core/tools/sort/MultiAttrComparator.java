package org.harper.frm.core.tools.sort;

import java.util.Comparator;

import org.harper.frm.core.tools.bean.BeanAccess;



public class MultiAttrComparator<T> implements Comparator<T> {

	private String[] attrs;

	private boolean[] asc;

	public MultiAttrComparator(String[] attrs, boolean[] ascending) {
		this.attrs = attrs;
		this.asc = ascending;
	}

	@SuppressWarnings("unchecked")
	public int compare(T a, T b) {
		try {
			for (int i = 0; i < attrs.length; i++) {
				Comparable at = (Comparable) BeanAccess.getInstance().get(
						attrs[i], a);
				Comparable bt = (Comparable) BeanAccess.getInstance().get(
						attrs[i], b);
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
