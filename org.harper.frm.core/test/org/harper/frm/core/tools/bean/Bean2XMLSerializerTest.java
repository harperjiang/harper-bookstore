package org.harper.frm.core.tools.bean;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;

import org.harper.frm.core.tools.bean.Bean2XMLSerializer;
import org.junit.Test;

public class Bean2XMLSerializerTest {

	@Test
	public void testSerialize() throws Exception {
		SampleBean sb = new SampleBean();
		sb.AttrA = "testAttrA";
		sb.AttrB = "testAttrB";
		sb.SubElement = "SubElemValue";

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bean2XMLSerializer.serialize(baos, sb, "SampleBean");
		baos.close();
		String a = baos.toString();

		assertEquals(
				"<SampleBean xmlns=\"abcdef\" ss:AttrA=\"testAttrA\" AttrB=\"testAttrB\"><SubElement>SubElemValue</SubElement></SampleBean>",
				a.trim());
	}

}
