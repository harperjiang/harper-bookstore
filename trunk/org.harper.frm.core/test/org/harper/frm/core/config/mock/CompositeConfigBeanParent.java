package org.harper.frm.core.config.mock;

import org.harper.frm.core.config.ConfigBeanSupport;
import org.harper.frm.core.config.ConfigKey;
import org.harper.frm.core.config.NestedBean;

public class CompositeConfigBeanParent extends ConfigBeanSupport {

	@ConfigKey("ATTR_A")
	private String attrA;

	@NestedBean
	private NormalConfigBean normalBean;

	public CompositeConfigBeanParent() {
		super();
		normalBean = new NormalConfigBean();
	}

	public String getAttrA() {
		return attrA;
	}

	public void setAttrA(String attrA) {
		this.attrA = attrA;
	}

	public NormalConfigBean getNormalBean() {
		return normalBean;
	}

	public void setNormalBean(NormalConfigBean normalBean) {
		this.normalBean = normalBean;
	}

}
