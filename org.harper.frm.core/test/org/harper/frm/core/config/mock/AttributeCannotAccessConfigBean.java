package org.harper.frm.core.config.mock;

import org.harper.frm.core.config.ConfigBeanSupport;
import org.harper.frm.core.config.ConfigKey;

public class AttributeCannotAccessConfigBean extends ConfigBeanSupport {

	

	private String getAttribu() {
		return attribute;
	}

	private void setAttribu(String attribute) {
		this.attribute = attribute;
	}

	@ConfigKey("ATTRIBUTE")
	private String attribute;
}
