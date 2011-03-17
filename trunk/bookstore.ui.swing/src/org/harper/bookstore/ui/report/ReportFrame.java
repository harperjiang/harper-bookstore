package org.harper.bookstore.ui.report;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import org.harper.frm.gui.swing.comp.textfield.DateTextField;
import org.harper.frm.gui.swing.comp.window.JPowerWindowEditor;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public abstract class ReportFrame extends JPowerWindowEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4088209175174137830L;

	private DateTextField fromDateField;

	private DateTextField toDateField;

	private JButton searchButton;

	private JFreeChart chart;

	private JPanel summaryPanel;

	private JTable dataTable;

	public ReportFrame(String title) {
		super(title);
		setSize(800, 600);
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);

		topPanel.add(new JLabel(Messages
				.getString("ReportFrame.label_start_time"))); //$NON-NLS-1$
		fromDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd")); //$NON-NLS-1$
		fromDateField.setPreferredSize(new Dimension(120, 25));
		topPanel.add(fromDateField);
		topPanel.add(new JLabel(Messages
				.getString("ReportFrame.label_stop_time"))); //$NON-NLS-1$
		toDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd")); //$NON-NLS-1$
		toDateField.setPreferredSize(new Dimension(120, 25));
		topPanel.add(toDateField);

		searchButton = new JButton(Messages.getString("ReportFrame.btn_search")); //$NON-NLS-1$
		topPanel.add(searchButton);

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
		
		summaryPanel = new JPanel();
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(summaryPanel,BorderLayout.NORTH);

		dataTable = createTable();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(dataTable);
		bottomPanel.add(scrollPane,BorderLayout.CENTER);
		
		centerPanel.add(bottomPanel);
	}

	protected abstract JFreeChart createChart();

	protected abstract JTable createTable();

	public DateTextField getFromDateField() {
		return fromDateField;
	}

	public DateTextField getToDateField() {
		return toDateField;
	}

	public JFreeChart getChart() {
		return chart;
	}

	public JButton getSearchButton() {
		return searchButton;
	}

	public JTable getDataTable() {
		return dataTable;
	}

	public JPanel getSummaryPanel() {
		return summaryPanel;
	}

}
