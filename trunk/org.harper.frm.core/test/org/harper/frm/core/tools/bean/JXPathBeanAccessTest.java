package org.harper.frm.core.tools.bean;

import org.harper.frm.core.tools.bean.JXPathBeanAccess;
import org.junit.Assert;
import org.junit.Test;

public class JXPathBeanAccessTest {

	@Test
	public void testGetArray() {
		String[] objects = new String[]{"a","b","c"};
		Assert.assertEquals("a",JXPathBeanAccess.get(objects, ".[1]"));
	}

	@Test
	public void testSetArray() {
		String[] objects = new String[]{"a","b","c"};
		JXPathBeanAccess.set(objects, ".[1]", "wt");
		Assert.assertEquals("wt",objects[0]);
	}

}
