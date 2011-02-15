package org.harper.frm.core.tools.sort;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.harper.frm.core.tools.sort.HeapSorter;
import org.harper.frm.core.tools.sort.QuickSorter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SorterTest {

	List<String> values = new ArrayList<String>();
	List<String> values2 = new ArrayList<String>();

	List<TempClass> valuest = new ArrayList<TempClass>();

	public static class TempClass {
		public TempClass(String s) {
			this.s = s;
		}

		private String s;

		public String getS() {
			return s;
		}

		public void setS(String s) {
			this.s = s;
		}

	}

	@Before
	public void setUp() throws Exception {
		for (int i = 0; i < 2000; i++) {
			Random len = new Random(i);
			int length = (int) (len.nextDouble() * 5);
			String str = "";
			for (int j = 0; j < length; j++) {
				str += (char) ('a' + len.nextDouble() * 25);
			}
			values.add(str);
			values2.add(str);
			valuest.add(new TempClass(str));
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSortListOfTComparatorOfT() {
		new HeapSorter(true).sort(values);
		new QuickSorter(true).sort(values2);
		assertEquals(values, values2);
	}

	@Test
	public void testMultiAttrComp() {
		new HeapSorter(false).sort(valuest, new String[] { "s" },
				new boolean[1]);
	}
}
