package org.harper.bookstore.ui.print;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.harper.bookstore.domain.profile.ContactInfo;
import org.harper.bookstore.domain.setting.express.ExpressOrderSettingBean;
import org.harper.frm.gui.swing.print.comp.Container;
import org.harper.frm.gui.swing.print.comp.TextLabel;

public class ExpressOrderLayout extends Container {

	private TextLabel fromName;

	private TextLabel fromAddress;

	private TextLabel fromPhone;

	private TextLabel fromMobile;

	private TextLabel fromSign;

	private TextLabel date;

	private TextLabel remark;

	private TextLabel toName;

	private TextLabel toAddress;

	private TextLabel toPhone;

	private TextLabel toMobile;

	public ExpressOrderLayout(ExpressOrderSettingBean bean) {
		super();

		setFont(new Font("宋体", Font.BOLD, 14));

		setPosition(new Rectangle(new Point(0, 0), bean.getSize()));

		fromName = new TextLabel();
		fromName.setPosition(bean.getFromNameLoc());
		add(fromName);

		fromAddress = new TextLabel();
		fromAddress.setPosition(bean.getFromAddressLoc());
		add(fromAddress);

		fromPhone = new TextLabel();
		fromPhone.setPosition(bean.getFromPhoneLoc());
		add(fromPhone);

		fromMobile = new TextLabel();
		fromMobile.setPosition(bean.getFromMobileLoc());
		add(fromMobile);

		remark = new TextLabel();
		remark.setPosition(bean.getRemarkLoc());
		add(remark);

		fromSign = new TextLabel();
		fromSign.setPosition(bean.getFromSignLoc());
		add(fromSign);

		date = new TextLabel();
		date.setPosition(bean.getDateLoc());
		add(date);

		toName = new TextLabel();
		toName.setPosition(bean.getToNameLoc());
		add(toName);

		toAddress = new TextLabel();
		toAddress.setPosition(bean.getToAddressLoc());
		add(toAddress);

		toPhone = new TextLabel();
		toPhone.setPosition(bean.getToPhoneLoc());
		add(toPhone);

		toMobile = new TextLabel();
		toMobile.setPosition(bean.getToMobileLoc());
		add(toMobile);

		date.setText(new SimpleDateFormat("yyyy MM dd").format(new Date()));
	}

	public void setFromInfo(ContactInfo from) {
		fromName.setText(from.getName());
		fromAddress.setText(from.getAddress());
		fromPhone.setText(from.getPhone());
		fromMobile.setText(from.getMobile());
		fromSign.setText(from.getName());
	}

	public void setToInfo(ContactInfo to) {
		toName.setText(to.getName());
		toAddress.setText(to.getAddress());
		toPhone.setText(to.getPhone());
		toMobile.setText(to.getMobile());
	}

	public void setRemark(String remarkText) {
		remark.setText(remarkText);
	}
}
