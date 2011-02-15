package org.harper.bookstore.ui.report;

import org.harper.bookstore.service.ReportService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.manager.BindingManager;

public class PurchaseReportController extends Controller {

	private PurchaseReportFrame frame;
	
	private PurchaseReportBean bean;
	
	public PurchaseReportController(PurchaseReportBean bean) {
		super();
		this.bean = bean;
		this.frame = new PurchaseReportFrame();
		frame.setController(this);
		
		initManager();
	}
	
	public PurchaseReportController() {
		this(new PurchaseReportBean());
	}
	
	protected void initManager() {
		manager = new BindingManager(bean);
		
		manager.addBinding(frame.getStartDateField().new DateTextBinding("startDate"));
		manager.addBinding(frame.getStopDateField().new DateTextBinding("stopDate"));
		manager.addBinding(new TableBinding(frame.getResultTable(),"result"));
		
		manager.loadAll();
	}
	
	public void search() {
		bean.setResult(new ReportService().purchaseReport(bean.getStartDate(),bean.getStopDate()));
	}
	
	public static void main(String[] args) {
		new PurchaseReportController();
	}
}
