package org.harper.frm.core.config.mock;

import org.harper.frm.core.config.ConfigBean;
import org.harper.frm.core.config.Configurable;
import org.harper.frm.core.config.ConfigurableSupport;

public class ConfigurableMock extends ConfigurableSupport implements
		Configurable {

	public Class<? extends ConfigBean> getConfigClass() {
		return NormalConfigBean.class;
	}

}
