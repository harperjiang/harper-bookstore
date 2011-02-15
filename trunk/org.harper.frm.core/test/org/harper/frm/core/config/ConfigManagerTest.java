package org.harper.frm.core.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.harper.frm.core.config.ConfigBean;
import org.harper.frm.core.config.ConfigManager;
import org.harper.frm.core.config.ConfigProvider;
import org.harper.frm.core.config.KeyNotFoundException;
import org.harper.frm.core.config.mock.AttrNotSupportConfigBean;
import org.harper.frm.core.config.mock.AttributeCannotAccessConfigBean;
import org.harper.frm.core.config.mock.AttributeCannotAccessConfigBean2;
import org.harper.frm.core.config.mock.CannotConfigBean;
import org.harper.frm.core.config.mock.CannotConfigBean2;
import org.harper.frm.core.config.mock.CompositeConfigBeanParent;
import org.harper.frm.core.config.mock.ConfigProviderMock;
import org.harper.frm.core.config.mock.ConfigurableMock;
import org.harper.frm.core.config.mock.ExceptionConfigBean;
import org.harper.frm.core.config.mock.NormalConfigBean;
import org.harper.frm.core.config.mock.NullResultConfigBean;
import org.junit.Before;
import org.junit.Test;



public class ConfigManagerTest {

	ConfigProvider provider = null;

	@Before
	public void setUp() {
		provider = new ConfigProviderMock();
		ConfigManager.getInstance().setProvider(provider);
		ConfigManager.getInstance().config(new ConfigurableMock());
	}

	@Test
	public void testGetNormalConfigBean() {
		ConfigBean bean = ConfigManager.getInstance().getConfigBean(
				NormalConfigBean.class);
		assertEquals(bean.getClass(), NormalConfigBean.class);

		ConfigBean bean2 = ConfigManager.getInstance().getConfigBean(
				NormalConfigBean.class);
		Assert.assertEquals(bean, bean2);

		NormalConfigBean nbean = (NormalConfigBean) bean;
		assertEquals("valueATTR_A", nbean.getAttrA());
		assertEquals("valueATTR_B", nbean.getAttrB());

		assertEquals(null, nbean.getNoConfigKey());
		nbean.setNoConfigKey("abc");
		assertEquals("abc", nbean.getNoConfigKey());
	}

	@Test
	public void testGetCompositeConfigBean() {
		CompositeConfigBeanParent parent = (CompositeConfigBeanParent) ConfigManager
				.getInstance().getConfigBean(CompositeConfigBeanParent.class);
		Assert.assertEquals("valueATTR_A", parent.getNormalBean().getAttrA());
	}

	@Test
	public void testProvider() {
		Assert
				.assertEquals(provider, ConfigManager.getInstance()
						.getProvider());
	}

	@Test
	public void testConfig() {
		ConfigurableMock nmcb = new ConfigurableMock();
		ConfigManager.getInstance().config(nmcb);
		NormalConfigBean ncb = (NormalConfigBean) nmcb.getConfig();
		assertEquals(true, ncb.isAttrBoolean());
		assertEquals(3, ncb.getAttrInt());

		Assert.assertEquals("valueATTR_A", ncb.getAttrA());
		Assert.assertEquals("valueATTR_B", ncb.getAttrB());
	}

	@Test
	public void testCannotConfig() {
		try {
			ConfigManager.getInstance().getConfigBean(CannotConfigBean2.class);
			fail("");
		} catch (IllegalArgumentException e) {

		}
		try {
			ConfigManager.getInstance().getConfigBean(CannotConfigBean.class);
			fail("");
		} catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void testCannotConfig2() {
		try {
			ConfigManager.getInstance().getConfigBean(
					AttributeCannotAccessConfigBean2.class);
			fail("");
		} catch (IllegalArgumentException e) {

		}
		try {
			ConfigManager.getInstance().getConfigBean(
					AttributeCannotAccessConfigBean.class);
			fail("");
		} catch (IllegalArgumentException e) {

		}
	}

	@Test
	public void testAttrNotSupport() {
		try {
			ConfigManager.getInstance().getConfigBean(
					AttrNotSupportConfigBean.class);
			fail("");
		} catch (UnsupportedOperationException e) {

		}
	}

	@Test
	public void testNullResult() {

		NullResultConfigBean nrcb = ConfigManager.getInstance().getConfigBean(
				NullResultConfigBean.class);
		assertEquals(null, nrcb.getAttribute());
	}

	@Test
	public void testAccessValue() {
		assertEquals("valueCONFIG", ConfigManager.getInstance().getConfigValue(
				"CONFIG"));
	}

	@Test
	public void testGetBeanValue() {
		assertEquals("valueATTR_A", ConfigManager.getInstance().getConfigValue(
				NormalConfigBean.class, "attrA"));
	}
	
	@Test
	public void testKeyNotFound() {
		try {
			ConfigManager.getInstance().getConfigBean(
					ExceptionConfigBean.StringECB.class);
			fail("");
		} catch (KeyNotFoundException e) {

		}
		try {
			ConfigManager.getInstance().getConfigBean(
					ExceptionConfigBean.IntECB.class);
			fail("");
		} catch (KeyNotFoundException e) {

		}
		try {
			ConfigManager.getInstance().getConfigBean(
					ExceptionConfigBean.BooleanECB.class);
			fail("");
		} catch (KeyNotFoundException e) {

		}
	}
}
