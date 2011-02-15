package org.harper.frm.data.formatter;

import java.util.Iterator;

import org.harper.frm.data.DataModelHelper;
import org.harper.frm.data.formatter.ITableContentProvider;


public class SampleTableContentProvider3 implements
		ITableContentProvider<Object> {

	static final long MAX = 5;
	
	public SampleTableContentProvider3() {
		super();
//		for (int i = 0; i < 120000; i++)
//			arrayList.add(i);
	}

	public Object get(Object rowInput, int column) {
		return String.valueOf(rowInput) + ":\n" + column;
	}

	public int getColumnCount(Object input) {
		return 3;
	}

	public String[] getColumnNames(Object input) {
		return new String[]{"With .", "Without", "Without ."};
	}

	public Iterator<Object> iterator(Object input) {
		return new Iterator<Object>() {

			int count = 0;
			
			public boolean hasNext() {
				return count < MAX;
			}

			public Object next() {
				return count++;
			}

			public void remove() {	
				throw new UnsupportedOperationException();
			}
			
		};
	}

}
