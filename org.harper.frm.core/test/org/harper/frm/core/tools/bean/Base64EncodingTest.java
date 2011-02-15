package org.harper.frm.core.tools.bean;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.harper.frm.core.tools.bean.Base64Encoding;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class Base64EncodingTest {
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEncode() throws IOException {
		String a = "This is a line of test string";
		
		byte[] res = Base64Encoding.encode(a.getBytes());

		assertEquals(a,new String(Base64Encoding.decode(res)));
	}

	@Test
	public void testDecode() {
		String a = "This is a line of test string";
		
		byte[] res = Base64Encoding.encode(a.getBytes());
		
		assertEquals(a,new String(Base64Encoding.decode(res)));
	}

}
