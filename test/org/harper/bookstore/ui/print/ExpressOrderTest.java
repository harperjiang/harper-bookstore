package org.harper.bookstore.ui.print;

import java.awt.print.PrinterJob;

import org.harper.bookstore.domain.order.PurchaseOrder;
import org.harper.bookstore.domain.profile.ContactInfo;
import org.harper.bookstore.domain.setting.express.SFOrderSettingBean;
import org.harper.frm.gui.swing.print.ComponentPrintable;
import org.junit.Test;

public class ExpressOrderTest {

	@Test
	public void testExpressOrder() throws Exception {
		ExpressOrderLayout eo = new ExpressOrderLayout(new SFOrderSettingBean());

		ContactInfo ci = new ContactInfo();
		ci.setName("cndebbie");
		ci.setAddress("上海市长宁区虹桥路1829弄52号601室卫生间马桶下第三个洞口");
		ci.setMobile("13166177118");

		eo.setFromInfo(ci);

		PurchaseOrder po = new PurchaseOrder();
		po.getContact().setName("cndebbie");
		po.getContact().setAddress("上海市长宁区虹桥路1829弄52号601室卫生间马桶下第三个洞口");
		po.getContact().setMobile("13166177118");

		eo.setToInfo(po.getContact());

		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(eo));
		if (job.printDialog())
			job.print();
	}

}
