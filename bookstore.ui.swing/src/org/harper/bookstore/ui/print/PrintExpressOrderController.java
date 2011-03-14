package org.harper.bookstore.ui.print;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileOutputStream;
import java.util.Properties;

import org.harper.bookstore.domain.profile.ContactInfo;
import org.harper.bookstore.domain.setting.express.ExpressOrderSettingBean;
import org.harper.bookstore.service.PrintService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JComboBinding;
import org.harper.frm.gui.swing.manager.JTextBinding;
import org.harper.frm.gui.swing.print.ComponentPrintable;

public class PrintExpressOrderController extends Controller {

	private PrintExpressOrderFrame frame;

	private BindingManager manager;

	private PrintExpressOrderBean bean;

	public PrintExpressOrderController() {
		super();

		frame = new PrintExpressOrderFrame(this);
		bean = new PrintExpressOrderBean();
		initManager();
	}

	private void initManager() {
		manager = new BindingManager(bean);
		manager
				.addBinding(new JComboBinding(frame.getCompanyCombo(),
						"company"));
		manager.addBinding(new JTextBinding(
				frame.getFromPanel().getNameField(), "fromName"));
		manager.addBinding(new JTextBinding(frame.getFromPanel()
				.getAddressArea(), "fromAddress"));
		manager.addBinding(new JTextBinding(frame.getFromPanel()
				.getMobileField(), "fromMobile"));
		manager.addBinding(new JTextBinding(frame.getToPanel().getNameField(),
				"toName"));
		manager.addBinding(new JTextBinding(
				frame.getToPanel().getAddressArea(), "toAddress"));
		manager.addBinding(new JTextBinding(
				frame.getToPanel().getMobileField(), "toMobile"));

		manager.loadAll();
	}

	public void setBean(PrintExpressOrderBean bean) {
		this.bean = bean;
		this.manager.setBean(bean);
	}

	public void print() {

		// Get Config Bean According to Company
		ExpressOrderSettingBean printbean = new PrintService()
				.getPrintSetting(bean.getCompany());

		ExpressOrderLayout layout = new ExpressOrderLayout(printbean);

		ContactInfo from = new ContactInfo();
		from.setName(bean.getFromName());
		from.setAddress(bean.getFromAddress());
		from.setMobile(bean.getFromMobile());

		ContactInfo to = new ContactInfo();
		to.setName(bean.getToName());
		to.setAddress(bean.getToAddress());
		to.setMobile(bean.getToMobile());

		layout.setFromInfo(from);
		layout.setToInfo(to);
		bean.save();
		
		// Print
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(layout));
		if (job.printDialog()) {
			try {
				job.print();
			} catch (PrinterException e) {
				throw new RuntimeException(e);
			}
			
		}
	}

	public static void main(String[] args) throws Exception {
		new PrintExpressOrderController();
	}
}
