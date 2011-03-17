package org.harper.bookstore.ui.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import org.harper.bookstore.service.bean.report.SellAndProfitResultBean;
import org.harper.bookstore.ui.Controller;
import org.harper.bookstore.ui.report.SellAndProfitReportBean.SellAndProfitCategoryData;
import org.harper.bookstore.util.Utilities;
import org.harper.frm.gui.swing.comp.table.TableBinding;
import org.harper.frm.gui.swing.freechart.CategoryChartBinding;
import org.harper.frm.gui.swing.manager.BindingManager;

public class SellAndProfitReportController extends Controller {

	private SellAndProfitReportBean bean;

	private SellAndProfitReportFrame frame;

	public SellAndProfitReportController() {
		super();
		bean = new SellAndProfitReportBean() {
			{
				setToDate(Utilities.getEndOfDate());
				setFromDate(Utilities.getBeginOfDate(30));
			}
		};

		frame = new SellAndProfitReportFrame();
		frame.setController(this);

		initManager();
	}

	private void initManager() {
		manager = new BindingManager(bean);
		manager.addBinding(frame.getFromDateField().new DateTextBinding(
				"fromDate"));
		manager.addBinding(frame.getToDateField().new DateTextBinding("toDate"));
		manager.addBinding(new CategoryChartBinding(frame.getChart()
				.getCategoryPlot().getDataset(), "datas"));
		manager.addBinding(new TableBinding(frame.getDataTable(), "originDatas"));
		manager.loadAll();
	}

	public SellAndProfitResultBean load() {
		// TODO Invoke Server

		// MOCK, mock data
		SellAndProfitResultBean result = new SellAndProfitResultBean() {
			{
				setDatas(new ArrayList<SellAndProfitData>());
				getDatas().add(
						new SellAndProfitData(Utilities.getBeginOfDate(10),
								new BigDecimal(8200), new BigDecimal(1200)));
				getDatas().add(
						new SellAndProfitData(Utilities.getBeginOfDate(9),
								new BigDecimal(3700), new BigDecimal(200)));
				getDatas().add(
						new SellAndProfitData(Utilities.getBeginOfDate(8),
								new BigDecimal(3200), new BigDecimal(400)));
				getDatas().add(
						new SellAndProfitData(Utilities.getBeginOfDate(7),
								new BigDecimal(11200), new BigDecimal(4900)));
				getDatas().add(
						new SellAndProfitData(Utilities.getBeginOfDate(6),
								new BigDecimal(8100), new BigDecimal(2600)));
				getDatas().add(
						new SellAndProfitData(Utilities.getBeginOfDate(5),
								new BigDecimal(3400), new BigDecimal(1700)));
				getDatas().add(
						new SellAndProfitData(Utilities.getBeginOfDate(4),
								new BigDecimal(4900), new BigDecimal(2500)));
				getDatas().add(
						new SellAndProfitData(Utilities.getBeginOfDate(3),
								new BigDecimal(3600), new BigDecimal(600)));
				getDatas().add(
						new SellAndProfitData(Utilities.getBeginOfDate(2),
								new BigDecimal(4400), new BigDecimal(1700)));
				getDatas().add(
						new SellAndProfitData(Utilities.getBeginOfDate(1),
								new BigDecimal(8800), new BigDecimal(2560)));
			}
		};

		return result;
	}

	public SellAndProfitReportBean getBean() {
		return bean;
	}

	@Override
	public JComponent getComponent() {
		return frame;
	}

}
