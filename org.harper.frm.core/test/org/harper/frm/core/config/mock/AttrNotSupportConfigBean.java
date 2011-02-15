package org.harper.frm.core.config.mock;

import java.math.BigDecimal;

import org.harper.frm.core.config.ConfigBeanSupport;
import org.harper.frm.core.config.ConfigKey;



public class AttrNotSupportConfigBean extends ConfigBeanSupport {

	
	public BigDecimal getAttribute() {
		return attribute;
	}

	public void setAttribute(BigDecimal attribute) {
		this.attribute = attribute;
	}

	@ConfigKey("ATTRIBUTE")
	private BigDecimal attribute;
}
