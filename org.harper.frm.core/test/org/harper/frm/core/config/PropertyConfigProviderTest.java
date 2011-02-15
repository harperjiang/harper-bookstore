package org.harper.frm.core.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.harper.frm.core.config.PropertyConfigProvider;
import org.junit.Before;
import org.junit.Test;

public class PropertyConfigProviderTest {

	private PropertyConfigProvider pcp;
	@Before
	public void before() {
		pcp = new PropertyConfigProvider();
	}
	
	@Test
	public void testLoadBooleanString() {
		assertEquals(true,pcp.loadBoolean("KEY_BOOL"));
	}

	@Test
	public void testLoadIntString() {
		assertEquals(5,pcp.loadInt("KEY_INT"));
	}

	@Test
	public void testLoadStringString() {
		assertEquals("a",pcp.loadString("KEY_A"));
		assertEquals("b",pcp.loadString("KEY_B"));
	}

	@Test
	public void testLoadBooleanStringString() {
		assertEquals(false,pcp.loadBoolean("header","KEY_BOOL"));
	}

	@Test
	public void testLoadIntStringString() {
		assertEquals(10,pcp.loadInt("header","KEY_INT"));
		try {
			assertEquals(5,pcp.loadInt("header","KEY_A"));
			fail("");
		} catch (IllegalArgumentException e) {
			
		}
	}

	@Test
	public void testLoadStringStringString() {
		assertEquals("b",pcp.loadString("header","KEY_A"));
		assertEquals("a",pcp.loadString("header","KEY_B"));
	}
	
	@Test
	public void testLoadString2StringString() {
		assertEquals("a",pcp.loadString("header2","KEY_A"));
		assertEquals("b",pcp.loadString("header2","KEY_B"));
	}
}
