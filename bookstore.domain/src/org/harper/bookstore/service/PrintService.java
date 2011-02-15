package org.harper.bookstore.service;

import org.harper.bookstore.domain.deliver.ExpressCompany;
import org.harper.bookstore.domain.setting.express.ExpressOrderSettingBean;

public class PrintService extends Service {

	public ExpressOrderSettingBean getPrintSetting(ExpressCompany company) {
		// TODO Allow Saving
		try {
			return (ExpressOrderSettingBean) company.getDefBeanClass()
					.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
