package org.harper.frm.data;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MemoryTableTest {

	@Before
	public void before() {

	}

	@Test
	public void testNameFaster() {
		int count = 1000000;
		long start = System.currentTimeMillis();
		List<String> a = genNames1(count);
		System.out.println(System.currentTimeMillis() - start);
		start = System.currentTimeMillis();
		List<String> b = genName2(count);
		System.out.println(System.currentTimeMillis() - start);

		assertEquals(a, b);

	}

	// Method from AbstractTableModel
	protected List<String> genNames1(int total) {
		ArrayList<String> results = new ArrayList<String>();
		for (int i = 0; i < total; i++) {
			int j = i;
			String result = "";
			for (; j >= 0; j = j / 26 - 1) {
				result = (char) ((char) (j % 26) + 'A') + result;
			}
			results.add(result);
		}
		return results;
	}

	// My method
	protected ArrayList<String> genName2(int column) {
		ArrayList<String> columnNames = new ArrayList<String>();
		for (int i = 0; i < column; i++) {
			if (i < 26)
				columnNames.add(new String(new char[] { (char) ('A' + i) }));
			else {
				StringBuilder newname = new StringBuilder();
				String last = columnNames.get(i - 1);
				char[] chars = last.toCharArray();
				int count = chars.length - 1;
				for (count = chars.length - 1; count >= 0; count--)
					if (chars[count] < 'Z') {
						chars[count] += 1;
						break;
					} else {
						chars[count] = 'A';
					}
				if (count == -1)
					newname.append('A');
				newname.append(chars);
				columnNames.add(newname.toString());
			}
		}
		return columnNames;
	}
}
