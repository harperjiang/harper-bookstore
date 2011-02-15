package org.harper.frm.core.logging;

import org.harper.frm.core.config.ConfigBeanSupport;
import org.harper.frm.core.config.ConfigKey;

public class LogConfigBean extends ConfigBeanSupport {

	@ConfigKey("LOG_DISABLED")
	private boolean disableLog;

	public boolean isDisableLog() {
		return this.disableLog;
	}

	public void setDisableLog(boolean disableLog) {
		this.disableLog = disableLog;
	}

	public LogConfigBean() {
		super();
	}
}
