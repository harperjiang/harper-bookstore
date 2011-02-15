package org.harper.frm.core.config.mock;

import org.harper.frm.core.config.ConfigBeanSupport;
import org.harper.frm.core.config.ConfigKey;

public class NullResultConfigBean extends ConfigBeanSupport {



	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	@ConfigKey("NULL_VALUE")
	private String attribute;
}
