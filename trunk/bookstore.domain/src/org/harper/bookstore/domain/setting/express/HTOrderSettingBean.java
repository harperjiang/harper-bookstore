package org.harper.bookstore.domain.setting.express;

import java.awt.Dimension;
import java.awt.Rectangle;

public class HTOrderSettingBean extends ExpressOrderSettingBean {

	public HTOrderSettingBean() {
		super();
		
		
		setSize(new Dimension(652, 358));

		setFromNameLoc(new Rectangle(100, 80, 72, 16));
		setFromAddressLoc(new Rectangle(85, 132, 170, 45));
		setFromPhoneLoc(new Rectangle(128, 182, 170, 16));
		setFromMobileLoc(new Rectangle(233, 182, 150, 16));

		setFromSignLoc(new Rectangle(85, 284, 72, 16));
		setDateLoc(new Rectangle(71, 298, 70, 16));

		setToNameLoc(new Rectangle(379, 80, 72, 16));
		setToAddressLoc(new Rectangle(340, 132, 170, 45));
		setToPhoneLoc(new Rectangle(397, 182, 170, 16));
		setToMobileLoc(new Rectangle(512, 182, 150, 16));
	}
}
