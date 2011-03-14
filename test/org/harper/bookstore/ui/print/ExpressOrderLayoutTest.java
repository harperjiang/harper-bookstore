package org.harper.bookstore.ui.print;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import org.harper.bookstore.domain.profile.ContactInfo;
import org.harper.bookstore.domain.setting.express.YundaOrderSettingBean;
import org.harper.bookstore.ui.setting.ContactInfoBean;
import org.harper.frm.gui.swing.print.ComponentPrintable;
import org.junit.Test;

public class ExpressOrderLayoutTest {

	@Test
	public void testExpressOrderLayout() {
		ExpressOrderLayout layout = new ExpressOrderLayout(
				new YundaOrderSettingBean());

		// From Info;
		ContactInfoBean ciBean = new ContactInfoBean();
		ciBean.load();

		ContactInfo fromInfo = new ContactInfo();
		fromInfo.setAddress("Sample From Address");
		fromInfo.setMobile("Sample From Mobile");
		fromInfo.setName("Sample From Name");
		layout.setFromInfo(fromInfo);

		// To Info
		ContactInfo toInfo = new ContactInfo();
		toInfo.setName("Sample Name");
		toInfo.setAddress("Sample Address");
		toInfo.setMobile("Sample Mobile");
		toInfo.setPhone("Sample Phone");
		layout.setToInfo(toInfo);

		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(layout));
		if (job.printDialog())
			try {
				job.print();
			} catch (PrinterException e) {
				throw new RuntimeException(e);
			}
	}

}
