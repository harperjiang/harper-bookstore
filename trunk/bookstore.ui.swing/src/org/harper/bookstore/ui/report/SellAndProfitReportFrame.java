package org.harper.bookstore.ui.report;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import org.harper.bookstore.service.bean.report.SellAndProfitResultBean;
import org.harper.bookstore.ui.common.UIStandard;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.textfield.DateTextField;
import org.harper.frm.gui.swing.comp.window.JPowerWindowEditor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;

public class SellAndProfitReportFrame extends JPowerWindowEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4412458569683474646L;

	private DateTextField fromDateField;

	private DateTextField toDateField;

	private JFreeChart chart;

	private JTable dataTable;

	private SellAndProfitReportController controller;

	public SellAndProfitReportFrame() {
		super(Messages.getString("SellAndProfitReportFrame.title")); //$NON-NLS-1$
		setSize(800, 600);
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);

		topPanel.add(new JLabel(Messages
				.getString("SellAndProfitReportFrame.label_start_time"))); //$NON-NLS-1$
		fromDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd")); //$NON-NLS-1$
		topPanel.add(fromDateField);
		topPanel.add(new JLabel(Messages
				.getString("SellAndProfitReportFrame.label_stop_time"))); //$NON-NLS-1$
		toDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd")); //$NON-NLS-1$
		topPanel.add(toDateField);

		JButton searchButton = new JButton(
				Messages.getString("SellAndProfitReportFrame.btn_search")); //$NON-NLS-1$
		topPanel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<SellAndProfitResultBean, Object>() {
					@Override
					protected SellAndProfitResultBean doInBackground()
							throws Exception {
						return getController().load();
					}

					protected void done() {
						try {
							SellAndProfitResultBean bean = get();
							getController().getBean().setOriginDatas(
									bean.getDatas());
						} catch (Exception e) {
							// TODO Log
							e.printStackTrace();
							JOptionPane.showMessageDialog(
									SellAndProfitReportFrame.this
											.getManagerWindow(),
									e.getMessage(),
									Messages.getString("SellAndProfitReportFrame.msg_search_title"), //$NON-NLS-1$

									JOptionPane.ERROR_MESSAGE);
						}
					};

				}.execute();
			}
		});

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(2, 1, 5, 5));
		centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(centerPanel, BorderLayout.CENTER);

		chart = createChart();
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setDoubleBuffered(true);
		chartPanel.setDomainZoomable(true);
		chartPanel.setFillZoomRectangle(true);
		chartPanel.setMouseWheelEnabled(true);

		centerPanel.add(chartPanel);

		dataTable = new JTable();
		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(SellAndProfitReportTableData.class);
		dataTable.setModel(ctm);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(dataTable);
		centerPanel.add(scrollPane);
	}

	private JFreeChart createChart() {
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		// create the chart...

		JFreeChart chart = ChartFactory
				.createStackedBarChart3D(
						Messages.getString("SellAndProfitReportFrame.chart_title"), Messages.getString("SellAndProfitReportFrame.chart_axis_x"), //$NON-NLS-1$ //$NON-NLS-2$
						Messages.getString("SellAndProfitReportFrame.chart_axis_y"), ds, PlotOrientation.VERTICAL, true, true, false); //$NON-NLS-1$

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

		// set the background color for the chart...
		chart.setBackgroundPaint(Color.white);
		// set fonts
		chart.getTitle().setFont(UIStandard.DEFAULT_FONT);
		chart.getLegend().setItemFont(UIStandard.DEFAULT_FONT);

		// get a reference to the plot for further customisation...
		CategoryPlot plot = (CategoryPlot) chart.getPlot();

		// ******************************************************************
		// More than 150 demo applications are included with the JFreeChart
		// Developer Guide...for more information, see:
		//
		// > http://www.object-refinery.com/jfreechart/guide.html
		//
		// ******************************************************************

		// set the range axis to display integers only...
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setLabelFont(UIStandard.DEFAULT_FONT);
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		// disable bar outlines...
		BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();
		renderer.setDrawBarOutline(true);
		renderer.setShadowVisible(true);

		// set up gradient paints for series...
		GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue, 0.0f,
				0.0f, new Color(0, 0, 64));
		GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f,
				0.0f, new Color(0, 64, 0));
		GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red, 0.0f,
				0.0f, new Color(64, 0, 0));
		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		renderer.setSeriesPaint(2, gp2);

		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(UIStandard.DEFAULT_FONT);
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions
				.createUpRotationLabelPositions(Math.PI / 6.0));
		// OPTIONAL CUSTOMISATION COMPLETED.

		// add some data to dataset

		return chart;
	}

	public SellAndProfitReportController getController() {
		return controller;
	}

	protected void setController(SellAndProfitReportController controller) {
		this.controller = controller;
	}

	public DateTextField getFromDateField() {
		return fromDateField;
	}

	public DateTextField getToDateField() {
		return toDateField;
	}

	public JFreeChart getChart() {
		return chart;
	}

	public JTable getDataTable() {
		return dataTable;
	}

	public static void main(String[] args) {
		new SellAndProfitReportFrame();
	}
}
