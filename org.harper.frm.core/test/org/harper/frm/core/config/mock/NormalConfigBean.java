package org.harper.frm.core.config.mock;

import org.harper.frm.core.config.ConfigBeanInfo;
import org.harper.frm.core.config.ConfigBeanSupport;
import org.harper.frm.core.config.ConfigKey;

@ConfigBeanInfo(name = "normal")
public class NormalConfigBean extends ConfigBeanSupport {

	@ConfigKey("ATTR_A")
	private String attrA;

	@ConfigKey("ATTR_B")
	private String attrB;

	@ConfigKey("ATTR_BOOLEAN")
	private boolean attrBoolean;

	@ConfigKey("ATTR_INT")
	private int attrInt;

	public boolean isAttrBoolean() {
		return attrBoolean;
	}

	public void setAttrBoolean(boolean attrBoolean) {
		this.attrBoolean = attrBoolean;
	}

	public int getAttrInt() {
		return attrInt;
	}

	public void setAttrInt(int attrInt) {
		this.attrInt = attrInt;
	}

	private String noConfigKey;

	public String getNoConfigKey() {
		return noConfigKey;
	}

	public void setNoConfigKey(String noConfigKey) {
		this.noConfigKey = noConfigKey;
	}

	public NormalConfigBean() {
		super();
	}

	public String getAttrA() {
		return attrA;
	}

	public void setAttrA(String attrA) {
		this.attrA = attrA;
	}

	public String getAttrB() {
		return attrB;
	}

	public void setAttrB(String attrB) {
		this.attrB = attrB;
	}

}
