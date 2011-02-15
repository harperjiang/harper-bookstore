package org.harper.frm.core.config.mock;

import org.harper.frm.core.config.ConfigBeanSupport;
import org.harper.frm.core.config.ConfigKey;

public class AttributeCannotAccessConfigBean2 extends ConfigBeanSupport {

	private void setAttribute(String attribute) {
		throw new RuntimeException();
	}

	private String getAttribute() {
		throw new RuntimeException();
	}

	@ConfigKey("ATTRIBUTE")
	private String attribute;
}
