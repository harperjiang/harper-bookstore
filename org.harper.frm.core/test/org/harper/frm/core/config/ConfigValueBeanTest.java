package org.harper.frm.core.config;

import static org.junit.Assert.*;

import org.harper.frm.core.AppContextManager;
import org.junit.Test;
import org.springframework.context.ApplicationContext;


public class ConfigValueBeanTest {

    @Test
    public void testRead() {
        ApplicationContext context = AppContextManager
                .getContext("spring_test.xml");
        
        Object test = context.getBean("test");
        assertNotNull(test);
        assertTrue(test instanceof String);
        assertEquals("123@ivo.com",test);
    }
}
