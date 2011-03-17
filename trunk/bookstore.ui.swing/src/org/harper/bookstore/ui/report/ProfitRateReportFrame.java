package org.harper.bookstore.ui.report;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.service.bean.report.ProfitRateResultBean;
import org.harper.bookstore.ui.common.PercentageTableCellRenderer;
import org.harper.bookstore.ui.common.UIStandard;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

public class ProfitRateReportFrame extends ReportFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2971429397796611704L;

	private ProfitRateReportController controller;

	public ProfitRateReportFrame() {
		super("Order Profit Rate Report");

		getSearchButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<ProfitRateResultBean, Object>() {

					@Override
					protected ProfitRateResultBean doInBackground()
							throws Exception {
						return getController().load();
					}

					@Override
					protected void done() {
						try {
							ProfitRateResultBean result = get();
							getController().getBean().setItems(
									result.getItems());
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}

				}.execute();
			}
		});
	}

	@Override
	protected JFreeChart createChart() {
		JFreeChart chart = ChartFactory.createPieChart3D("Order Profit Rate",
				new DefaultPieDataset(), // data
				true, // include legend
				true, false);

		chart.getTitle().setFont(UIStandard.DEFAULT_FONT);
		chart.getLegend().setItemFont(UIStandard.DEFAULT_FONT);

		chart.setBackgroundPaint(Color.white);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setSectionOutlinesVisible(false);
		plot.setDarkerSides(true);
		plot.setNoDataMessage("No data available");

		return chart;
	}

	@Override
	protected JTable createTable() {
		JTable table = new JTable();
		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(ProfitRateTableData.class);
		table.setModel(ctm);
		table.setDefaultRenderer(BigDecimal.class,
				new PercentageTableCellRenderer());
		table.setDefaultRenderer(Integer.TYPE, new DefaultTableCellRenderer());
		return table;
	}

	public ProfitRateReportController getController() {
		return controller;
	}

	public void setController(ProfitRateReportController controller) {
		this.controller = controller;
	}

}
