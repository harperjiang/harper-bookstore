package org.harper.frm.core.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.harper.frm.core.config.mock.NormalConfigBean;
import org.junit.Test;



public class ConfigBeanSupportTest {

	@Test
	public void testGetName() {
		NormalConfigBean ncb = new NormalConfigBean();
		assertEquals("normal",ncb.getName());
		ncb.setName("normalAgain");
		assertEquals("normalAgain",ncb.getName());
	}

	@Test
	public void testGetPairs() {
		NormalConfigBean ncb = new NormalConfigBean();
		Properties prop = ncb.getPairs();
		assertTrue(prop.containsKey("attrA"));
		assertTrue(prop.containsKey("attrB"));
		assertTrue(prop.containsKey("attrInt"));
		assertTrue(prop.containsKey("attrBoolean"));
	}

}
