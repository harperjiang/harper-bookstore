package org.harper.bookstore.domain.setting;

import org.harper.frm.core.config.ConfigBeanInfo;
import org.harper.frm.core.config.ConfigBeanSupport;
import org.harper.frm.core.config.ConfigKey;
import org.harper.frm.core.config.ConfigManager;

@ConfigBeanInfo(name = "store_setting")
public class StoreSettingBean extends ConfigBeanSupport {

	@ConfigKey("ALLOW_NEGATIVE_STOCK")
	private boolean allowNegativeStock;

	public boolean isAllowNegativeStock() {
		return allowNegativeStock;
	}

	public void setAllowNegativeStock(boolean allowNegativeStock) {
		this.allowNegativeStock = allowNegativeStock;
	}

	public static StoreSettingBean get() {
		return ConfigManager.getInstance()
				.getConfigBean(StoreSettingBean.class);
	}
}
