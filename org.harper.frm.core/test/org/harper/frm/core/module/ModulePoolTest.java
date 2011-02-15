package org.harper.frm.core.module;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.harper.frm.core.module.ModuleInitException;
import org.harper.frm.core.module.ModulePool;
import org.junit.Before;
import org.junit.Test;

public class ModulePoolTest {

	private ModulePool pool;

	DummyModule module1, module2, module3, module4, module5;

	@Before
	public void before() {
		pool = new ModulePool();
	}

	@Test
	public void testGetModules() {
		pool.getModules();
	}

	public void testInitModules() throws ModuleInitException {
		try {
			pool.initModules();
			fail();
		} catch (ModuleInitException e) {

		}
		pool.initModules();
		assertTrue(pool.getModule("module1").isInitialized());
		assertTrue(pool.getModule("module2").isInitialized());
		assertTrue(!pool.getModule("module3").isInitialized());
		assertTrue(!pool.getModule("module4").isInitialized());
		assertTrue(pool.getModule("module5").isInitialized());
	}

	public void testInitDestroyModules() throws ModuleInitException {
		pool.initModules();

		pool.destroyModules();
		assertTrue(!pool.getModule("module1").isInitialized());
		assertTrue(!pool.getModule("module2").isInitialized());
		assertTrue(!pool.getModule("module3").isInitialized());
		assertTrue(!pool.getModule("module4").isInitialized());
		assertTrue(!pool.getModule("module5").isInitialized());
	}

	@Test
	public void testErrorModule() {
		pool.addModule(new ErrorModule());
		try {
			pool.initModules();
			fail();
		} catch (ModuleInitException e) {

		}

	}

}
