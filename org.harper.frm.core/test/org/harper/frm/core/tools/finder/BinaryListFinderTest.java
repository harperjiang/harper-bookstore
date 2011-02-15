package org.harper.frm.core.tools.finder;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.harper.frm.core.tools.finder.BinaryFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BinaryListFinderTest {

	private List<String> strlist;

	@Before
	public void setUp() throws Exception {
		strlist = new ArrayList<String>();

		strlist.add("t");
		strlist.add("x");
		strlist.add("y");
		strlist.add("z");
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testFind() {
		assertEquals(strlist.get(1), new BinaryFinder().find(strlist, true,
				null, "x"));
	}

}
