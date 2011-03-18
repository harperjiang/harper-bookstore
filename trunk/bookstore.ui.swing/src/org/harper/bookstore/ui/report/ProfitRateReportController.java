package org.harper.bookstore.ui.report;

import javax.swing.JComponent;

import org.harper.bookstore.service.ReportService;
import org.harper.bookstore.service.bean.report.ProfitRateResultBean;
import org.harper.bookstore.ui.Controller;
import org.harper.bookstore.util.Utilities;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.freechart.ChartBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.jfree.chart.plot.PiePlot;

public class ProfitRateReportController extends Controller {

	private ProfitRateReportFrame frame;

	private ProfitRateReportBean bean;

	public ProfitRateReportController() {
		super();

		frame = new ProfitRateReportFrame();
		frame.setController(this);
		bean = new ProfitRateReportBean() {
			{
				setFromDate(Utilities.getBeginOfDate(30));
				setToDate(Utilities.getEndOfDate());
			}
		};
		initManager();
	}

	protected void initManager() {
		manager = new BindingManager(bean);
		manager.addBinding(frame.getFromDateField().new DateTextBinding(
				"fromDate"));
		manager.addBinding(frame.getToDateField().new DateTextBinding("toDate"));
		manager.addBinding(new ChartBinding(((PiePlot) frame.getChart()
				.getPlot()).getDataset(), "pieDatas"));
		manager.addBinding(new TableBinding(frame.getDataTable(), "items"));
		manager.loadAll();
	}

	public ProfitRateResultBean load() {
		// ProfitRateResultBean result = new ProfitRateResultBean() {
		// {
		// addItem(new ProfitRateItemBean(12, new BigDecimal(0),
		// new BigDecimal(0.1)));
		// addItem(new ProfitRateItemBean(21, new BigDecimal(0.1),
		// new BigDecimal(0.2)));
		// addItem(new ProfitRateItemBean(37, new BigDecimal(0.2),
		// new BigDecimal(0.3)));
		// addItem(new ProfitRateItemBean(5, new BigDecimal(0.3),
		// new BigDecimal(0.4)));
		// addItem(new ProfitRateItemBean(8, new BigDecimal(0.4),
		// new BigDecimal(0.5)));
		// }
		// };
		ProfitRateResultBean result = getRptService().getProfitRate(
				bean.getFromDate(), bean.getToDate());
		return result;
	}

	private ReportService rptService;

	public ReportService getRptService() {
		if (null == rptService)
			rptService = new ReportService();
		return rptService;
	}

	public void setRptService(ReportService rptService) {
		this.rptService = rptService;
	}

	public ProfitRateReportFrame getFrame() {
		return frame;
	}

	public ProfitRateReportBean getBean() {
		return bean;
	}

	@Override
	public JComponent getComponent() {
		return getFrame();
	}

}
