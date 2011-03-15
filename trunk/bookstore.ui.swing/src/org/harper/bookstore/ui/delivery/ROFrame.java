package org.harper.bookstore.ui.delivery;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.harper.bookstore.domain.deliver.ReceiveItem;
import org.harper.bookstore.domain.deliver.ReceiveOrder.ReceiveType;
import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.ui.common.CheckBoxTableRenderer;
import org.harper.bookstore.ui.common.ItemController;
import org.harper.bookstore.ui.common.ItemController.TableCreator;
import org.harper.bookstore.ui.common.LabeledTextArea;
import org.harper.bookstore.ui.common.SiteComboBox;
import org.harper.bookstore.ui.order.DeliveryPanel;
import org.harper.frm.gui.swing.comp.table.CommonTableModel;
import org.harper.frm.gui.swing.comp.table.data.TableData;
import org.harper.frm.gui.swing.comp.textfield.DateTextField;
import org.harper.frm.gui.swing.comp.textfield.NumTextField;
import org.harper.frm.gui.swing.comp.window.JPowerWindowEditor;

public class ROFrame extends JPowerWindowEditor {

	private DeliveryPanel panel;

	private DateTextField createDateField;

	private JComboBox typeCombo;

	private SiteComboBox siteCombo;

	private ItemController<ReceiveItem> roItemController;

	private LabeledTextArea remarkArea;

	private ROController controller;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4793108766253003738L;

	public ROFrame() {
		super("收/退货登记"); //$NON-NLS-1$
		setSize(800, 500);

		setLayout(new BorderLayout());

		JToolBar toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);
		createToolBar(toolBar);

		JPanel centerPane = new JPanel();
		add(centerPane, BorderLayout.CENTER);
		centerPane.setLayout(new BorderLayout());

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 6, 20, 10));
		topPanel.setBorder(new EmptyBorder(10, 30, 10, 30));

		topPanel.add(new JLabel("类型"));
		typeCombo = new JComboBox();
		typeCombo.addItem(ReceiveType.RETURN);
		typeCombo.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel superLabel = (JLabel) super
						.getListCellRendererComponent(list, value, index,
								isSelected, cellHasFocus);
				superLabel.setText(((ReceiveType) value).desc());
				return superLabel;
			}
		});

		topPanel.add(new JLabel("创建日期"));
		createDateField = new DateTextField(new SimpleDateFormat("yyyy-MM-dd"));
		topPanel.add(createDateField);

		topPanel.add(new JLabel("收货仓库"));
		siteCombo = new SiteComboBox(false, false);
		topPanel.add(siteCombo);

		centerPane.add(topPanel, BorderLayout.NORTH);

		JTabbedPane tabbedPane = new JTabbedPane();
		centerPane.add(tabbedPane, BorderLayout.CENTER);

		panel = new DeliveryPanel();
		tabbedPane.addTab("地址信息", panel); //$NON-NLS-1$

		remarkArea = new LabeledTextArea("备注"); //$NON-NLS-1$
		tabbedPane.addTab("备注", remarkArea);

		roItemController = new ItemController<ReceiveItem>(null,
				new TableCreator() {
					@Override
					public void createTable(JTable table) {
						CommonTableModel ctm = new CommonTableModel();
						ctm.initialize(ReceiveItemTableData.class);
						table.setModel(ctm);

						ctm.setCellEditable(2, true);

						table.setDefaultRenderer(Integer.TYPE,
								new DefaultTableCellRenderer());
						table.setDefaultRenderer(BigDecimal.class,
								new DefaultTableCellRenderer());
						table.setDefaultRenderer(Boolean.TYPE,
								new CheckBoxTableRenderer());
						table.setDefaultEditor(Integer.TYPE,
								new DefaultCellEditor(new NumTextField()));
						table.setDefaultEditor(BigDecimal.class,
								new DefaultCellEditor(new NumTextField()));
						table.setDefaultEditor(Boolean.TYPE,
								new DefaultCellEditor(new JCheckBox()));
					}
				}) {

			@Override
			protected ReceiveItem createItem(Book book) {
				ReceiveItem newItem = new ReceiveItem();
				newItem.setBook(book);
				newItem.setCount(1);
				return newItem;
			}

			@Override
			protected TableData createTableData(Object item) {
				return new ReceiveItemTableData(item);
			}

		};

		tabbedPane.add("Items", roItemController.getView()); //$NON-NLS-1$

		JPanel bottomPanel = new JPanel();
		add(bottomPanel, BorderLayout.SOUTH);
	}

	private void createToolBar(JToolBar toolBar) {
		JButton saveButton = new JButton("保存");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		toolBar.add(saveButton);

		JButton confirmButton = new JButton("确认收货");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		toolBar.add(confirmButton);

	}

	public DeliveryPanel getPanel() {
		return panel;
	}

	public ItemController<ReceiveItem> getRoItemController() {
		return roItemController;
	}

	public JTextArea getRemarkArea() {
		return remarkArea.getTextField();
	}

	public void setController(ROController controller) {
		this.controller = controller;
	}

	public ROController getController() {
		return controller;
	}

	public DateTextField getCreateDateField() {
		return createDateField;
	}

	public SiteComboBox getSiteCombo() {
		return siteCombo;
	}

	public JComboBox getTypeCombo() {
		return typeCombo;
	}

}
