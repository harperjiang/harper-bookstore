package org.harper.bookstore.ui.tbinterface;

import javax.swing.JComponent;

import org.harper.bookstore.domain.taobao.TradeQueryStatus;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.job.JobMonitor;
import org.harper.frm.job.tb.ImportTaobaoOrderJob;

public class TOPImportController extends Controller {

	TOPImportFrame frame;

	TOPImportBean bean;

	public TOPImportController() {
		super();

		frame = new TOPImportFrame();
		frame.setController(this);

		bean = new TOPImportBean();
		initManager();
	}

	protected void initManager() {
		manager = new BindingManager(bean);
		manager.addBinding(frame.getStartDateField().new DateTextBinding(
				"startDate"));
		manager.addBinding(frame.getStopDateField().new DateTextBinding(
				"stopDate"));
		manager.loadAll();
	}

	public int execute(JobMonitor monitor) {
		ImportTaobaoOrderJob job = new ImportTaobaoOrderJob();
		job.setStart(bean.getStartDate());
		job.setStop(bean.getStopDate());
		job.setStatus(TradeQueryStatus.WAIT_BUYER_CONFIRM_GOODS);
		int sent = (Integer) job.call(monitor);

		job.setStatus(TradeQueryStatus.WAIT_SELLER_SEND_GOODS);
		int wait = (Integer) job.call(monitor);

		job.setStatus(TradeQueryStatus.TRADE_FINISHED);
		int finished = (Integer) job.call(monitor);
		
		return sent + wait + finished;
	}

	@Override
	public JComponent getComponent() {
		return getFrame();
	}

	public TOPImportFrame getFrame() {
		return frame;
	}

	public static void main(String[] args) {
		new TOPImportController();
	}
}
