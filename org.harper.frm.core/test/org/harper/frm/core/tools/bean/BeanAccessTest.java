package org.harper.frm.core.tools.bean;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.harper.frm.core.tools.bean.BeanAccess;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BeanAccessTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCopyObject() {

    }

    @Test
    public void testGetter() {
        BeanAccessBean bean = new BeanAccessBean();
        bean.setBooleanVal(true);
        bean.setIntVal(4);
        bean.setLongVal(123l);
        bean.setStrVal("string");

        assertEquals(true, BeanAccess.getInstance().get("booleanVal", bean));
        assertEquals(4, BeanAccess.getInstance().get("intVal", bean));
        assertEquals(123l, BeanAccess.getInstance().get("longVal", bean));
        assertEquals(true, BeanAccess.getInstance().get("booleanVal", bean));
    }

    @Test
    public void testSetter() {
        BeanAccessBean bean = new BeanAccessBean();

        BeanAccess.getInstance().set("booleanVal", bean, true);

        assertEquals(true, bean.isBooleanVal());

        BeanAccess.getInstance().set("booleanVal", bean, "false");

        assertEquals(false, bean.isBooleanVal());

        BeanAccess.getInstance().set("intVal", bean, 14);

        assertEquals(14, bean.getIntVal());

        BeanAccess.getInstance().set("intVal", bean, "16");

        assertEquals(16, bean.getIntVal());

        BeanAccess.getInstance().set("longVal", bean, 424);

        assertEquals(424, bean.getLongVal());

        BeanAccess.getInstance().set("longVal", bean, "1231");

        assertEquals(1231, bean.getLongVal());

        BeanAccess.getInstance().set("strVal", bean, "this is a string");

        assertEquals("this is a string", bean.getStrVal());

        BeanAccess.getInstance().set("bigDecimal", bean, BigDecimal.ONE);
        
        assertEquals(BigDecimal.ONE,bean.getBigDecimal());
    }
}
