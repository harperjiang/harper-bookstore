package org.harper.frm.core.tools.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.harper.frm.core.tools.bean.BeanSerializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BeanSerializerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSerialize() {
		DemoBean dbd = new DemoBean();
		dbd.setAbc(true);
		dbd.setName("namedd");
		dbd.setValue(34);
		
		try {
			BeanSerializer.serialize(dbd);
			fail("");
		} catch (RuntimeException e) {
			
		}
		
		DemoBean2 db = new DemoBean2();
		db.setAbc(true);
		db.setName("namedd");
		db.setValue(34);
		
		String serialized = BeanSerializer.serialize(db);
		
		Object back = BeanSerializer.deserialize(serialized);
		
		assertEquals(DemoBean2.class,back.getClass());
		
		DemoBean2 backbean = (DemoBean2)back;
		
		assertEquals(true,backbean.isAbc());
		assertEquals("namedd",backbean.getName());
		assertEquals(34,backbean.getValue());
	}

}
