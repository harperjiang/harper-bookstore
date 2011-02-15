package org.harper.frm.core.tools.finder;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.harper.frm.core.tools.TrivalComparator;
import org.harper.frm.core.tools.finder.BinaryFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class BinaryFinderTest {

	List<String> values = new ArrayList<String>();
	
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
		}
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFind() {
		assertNotNull(new BinaryFinder().find(values,false, null, values.get(315)));
	}
	
	@Test
	public void testMyComparator() {
		assertNotNull(new BinaryFinder().find(values,false, new TrivalComparator(), values.get(315)));
	}

}
