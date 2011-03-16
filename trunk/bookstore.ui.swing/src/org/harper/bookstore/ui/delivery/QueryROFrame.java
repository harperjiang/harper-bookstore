package org.harper.bookstore.ui.delivery;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.harper.bookstore.domain.deliver.ReceiveOrder;
import org.harper.bookstore.ui.common.EnumListCellRenderer;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.textfield.DateTextField;
import org.harper.frm.gui.swing.comp.window.JPowerWindowEditor;

public class QueryROFrame extends JPowerWindowEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 732562746905953042L;

	private QueryROController controller;

	private DateTextField fromCreateDateField;

	private DateTextField toCreateDateField;
	
	private DateTextField fromReceiveDateField;

	private DateTextField toReceiveDateField;
	
	private JTextField senderNameField;

	private JTextField numberField;

	private JComboBox statusCombo;

	private JTable queryRoTable;

	public QueryROFrame() {
		super(Messages.getString("QueryROFrame.title"));  //$NON-NLS-1$
		setSize(800, 600);
		setLayout(new BorderLayout());

		createTopPanel();

		queryRoTable = new JTable();
		CommonTableModel ctm = new CommonTableModel();
		ctm.initialize(ReceiveOrderTableData.class);
		queryRoTable.setModel(ctm);

		queryRoTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() <= 1)
					return;
				int selected = queryRoTable.getSelectedRow();
				ReceiveOrder order = controller.getBean().getOrders()
						.get(selected);
				getManagerWindow().addEditor(
						new ROController(order).getComponent());
			}
		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(queryRoTable);
		add(scrollPane, BorderLayout.CENTER);
	}

	public QueryROController getController() {
		return controller;
	}

	public void setController(QueryROController controller) {
		this.controller = controller;
	}

	protected void createTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		topPanel.setLayout(new GridLayout(4, 4, 5, 5));

		JLabel fromCreateDateLabel = new JLabel(Messages.getString("QueryROFrame.label_createdate_start")); //$NON-NLS-1$
		topPanel.add(fromCreateDateLabel);
		fromCreateDateLabel.setPreferredSize(new Dimension(120, 25));

		fromCreateDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd")); //$NON-NLS-1$
		topPanel.add(fromCreateDateField);
		fromCreateDateField.setPreferredSize(new Dimension(150, 25));

		JLabel toCreateDateLabel = new JLabel(Messages.getString("QueryROFrame.label_createdate_stop"));  //$NON-NLS-1$
		topPanel.add(toCreateDateLabel);
		toCreateDateLabel.setPreferredSize(new Dimension(120, 25));

		toCreateDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd")); //$NON-NLS-1$
		topPanel.add(toCreateDateField);
		toCreateDateField.setPreferredSize(new Dimension(150, 25));

		JLabel senderNameLabel = new JLabel(Messages.getString("QueryROFrame.label_sender_name")); //$NON-NLS-1$
		topPanel.add(senderNameLabel);
		senderNameLabel.setPreferredSize(new Dimension(120, 25));

		senderNameField = new JTextField();
		senderNameField.setPreferredSize(new Dimension(120, 25));
		topPanel.add(senderNameField);

		JLabel numberLabel = new JLabel(Messages.getString("QueryROFrame.label_number"));  //$NON-NLS-1$
		topPanel.add(numberLabel);

		numberField = new JTextField();
		topPanel.add(numberField);

		JLabel statusLabel = new JLabel(Messages.getString("QueryROFrame.label_status"));  //$NON-NLS-1$
		topPanel.add(statusLabel);

		statusCombo = new JComboBox();
		statusCombo.addItem(null);
		for (ReceiveOrder.Status sta : ReceiveOrder.Status.values())
			statusCombo.addItem(sta);
		statusCombo
				.setRenderer(new EnumListCellRenderer(
						ReceiveOrder.Status.class,
						ResourceBundle
								.getBundle("org.harper.bookstore.domain.deliver.ROStatus"), //$NON-NLS-1$
						"ALL")); //$NON-NLS-1$
		topPanel.add(statusCombo);
		
		topPanel.add(new JLabel());
		topPanel.add(new JLabel());

		JButton searchButton = new JButton(Messages.getString("QueryROFrame.btn_query"));  //$NON-NLS-1$
		topPanel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getController().search();
			}
		});

		add(topPanel, BorderLayout.NORTH);
	}



	public DateTextField getFromCreateDateField() {
		return fromCreateDateField;
	}

	public DateTextField getToCreateDateField() {
		return toCreateDateField;
	}

	public DateTextField getFromReceiveDateField() {
		return fromReceiveDateField;
	}

	public DateTextField getToReceiveDateField() {
		return toReceiveDateField;
	}

	public JTextField getSenderNameField() {
		return senderNameField;
	}

	public JTextField getNumberField() {
		return numberField;
	}

	public JTable getQueryRoTable() {
		return queryRoTable;
	}

	public JComboBox getStatusCombo() {
		return statusCombo;
	}

}
