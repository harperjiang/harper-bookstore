package org.harper.frm.core.config.mock;

import org.harper.frm.core.config.ConfigBeanSupport;
import org.harper.frm.core.config.ConfigKey;

public class ExceptionConfigBean {

	public static final class StringECB extends ConfigBeanSupport {
		@ConfigKey("EXCEPTION")
		private String exceptionKey;

		public String getExceptionKey() {
			return exceptionKey;
		}

		public void setExceptionKey(String exceptionKey) {
			this.exceptionKey = exceptionKey;
		}
	}

	public static final class IntECB extends ConfigBeanSupport {
		@ConfigKey("EXCEPTION")
		private int exceptionKey;

		public int getExceptionKey() {
			return exceptionKey;
		}

		public void setExceptionKey(int exceptionKey) {
			this.exceptionKey = exceptionKey;
		}

	}

	public static final class BooleanECB extends ConfigBeanSupport {
		@ConfigKey("EXCEPTION")
		private boolean exceptionKey;

		public boolean isExceptionKey() {
			return exceptionKey;
		}

		public void setExceptionKey(boolean exceptionKey) {
			this.exceptionKey = exceptionKey;
		}

	}
}
