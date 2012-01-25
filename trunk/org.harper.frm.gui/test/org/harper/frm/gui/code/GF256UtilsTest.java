package org.harper.frm.gui.code;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GF256UtilsTest extends GF256Utils {

	@Test
	public void testAdd() {

	}

	@Test
	public void testMul() {
		assertEquals(54, GF256Utils.mul(0xb6, 0x53));
	}

	@Test
	public void testMaxbit() {
		assertEquals(0, GF256Utils.maxbit(1));
		assertEquals(1, GF256Utils.maxbit(2));
		assertEquals(1, GF256Utils.maxbit(3));
		assertEquals(2, GF256Utils.maxbit(4));
		assertEquals(2, GF256Utils.maxbit(5));
		assertEquals(2, GF256Utils.maxbit(6));
	}

	@Test
	public void testPow() {
		assertEquals(29,GF256Utils.pow(PRIME, 21));
		
	}
}
