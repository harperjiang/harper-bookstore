package org.harper.bookstore.ui.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.harper.bookstore.service.bean.report.SAPReportResultBean;
import org.harper.bookstore.ui.Controller;
import org.harper.bookstore.ui.report.SAPReportBean.SAPCategoryData;
import org.harper.bookstore.util.Utilities;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.freechart.ChartBinding;
import org.harper.frm.gui.swing.manager.BindingManager;

public class SAPReportController extends Controller {

	private SAPReportBean bean;

	private SAPReportFrame frame;

	public SAPReportController() {
		super();
		bean = new SAPReportBean() {
			{
				setToDate(Utilities.getEndOfDate());
				setFromDate(Utilities.getBeginOfDate(30));
			}
		};

		frame = new SAPReportFrame();
		frame.setController(this);

		initManager();
	}

	private void initManager() {
		manager = new BindingManager(bean);
		manager.addBinding(frame.getFromDateField().new DateTextBinding(
				"fromDate"));
		manager.addBinding(frame.getToDateField().new DateTextBinding("toDate"));
		manager.addBinding(new ChartBinding(frame.getChart()
				.getCategoryPlot().getDataset(), "datas"));
		manager.addBinding(new TableBinding(frame.getDataTable(), "originDatas"));
		manager.loadAll();
	}

	public SAPReportResultBean load() {
		// TODO Invoke Server

		// MOCK, mock data
		SAPReportResultBean result = new SAPReportResultBean() {
			{
				setDatas(new ArrayList<SAPData>());
				getDatas().add(
						new SAPData(Utilities.getBeginOfDate(10),
								new BigDecimal(8200), new BigDecimal(1200)));
				getDatas().add(
						new SAPData(Utilities.getBeginOfDate(9),
								new BigDecimal(3700), new BigDecimal(200)));
				getDatas().add(
						new SAPData(Utilities.getBeginOfDate(8),
								new BigDecimal(3200), new BigDecimal(400)));
				getDatas().add(
						new SAPData(Utilities.getBeginOfDate(7),
								new BigDecimal(11200), new BigDecimal(4900)));
				getDatas().add(
						new SAPData(Utilities.getBeginOfDate(6),
								new BigDecimal(8100), new BigDecimal(2600)));
				getDatas().add(
						new SAPData(Utilities.getBeginOfDate(5),
								new BigDecimal(3400), new BigDecimal(1700)));
				getDatas().add(
						new SAPData(Utilities.getBeginOfDate(4),
								new BigDecimal(4900), new BigDecimal(2500)));
				getDatas().add(
						new SAPData(Utilities.getBeginOfDate(3),
								new BigDecimal(3600), new BigDecimal(600)));
				getDatas().add(
						new SAPData(Utilities.getBeginOfDate(2),
								new BigDecimal(4400), new BigDecimal(1700)));
				getDatas().add(
						new SAPData(Utilities.getBeginOfDate(1),
								new BigDecimal(8800), new BigDecimal(2560)));
			}
		};

		return result;
	}

	public SAPReportBean getBean() {
		return bean;
	}

	@Override
	public JComponent getComponent() {
		return frame;
	}

}
