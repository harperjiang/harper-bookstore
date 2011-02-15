package org.harper.bookstore.domain.deliver;

import java.util.ResourceBundle;

import org.harper.bookstore.domain.setting.express.ExpressOrderSettingBean;
import org.harper.bookstore.domain.setting.express.HTOrderSettingBean;
import org.harper.bookstore.domain.setting.express.SFOrderSettingBean;
import org.harper.bookstore.domain.setting.express.YundaOrderSettingBean;

public enum ExpressCompany {

	YUNDA(YundaOrderSettingBean.class), HT(HTOrderSettingBean.class), SF(
			SFOrderSettingBean.class);

	Class<? extends ExpressOrderSettingBean> defBeanClass;

	ExpressCompany(Class<? extends ExpressOrderSettingBean> defBeanClass) {
		this.defBeanClass = defBeanClass;
	}

	public String getDisplayName() {
		ResourceBundle rb = ResourceBundle
				.getBundle("org.harper.bookstore.domain.setting.express.express_company");
		return rb.getString(name());
	}

	public Class<? extends ExpressOrderSettingBean> getDefBeanClass() {
		return defBeanClass;
	}

}
