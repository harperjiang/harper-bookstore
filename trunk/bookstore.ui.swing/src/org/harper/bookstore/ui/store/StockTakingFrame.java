package org.harper.bookstore.ui.store;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.harper.bookstore.domain.profile.Book;
import org.harper.bookstore.domain.store.StockTakingItem;
import org.harper.bookstore.ui.common.ItemController;
import org.harper.bookstore.ui.common.ItemController.TableCreator;
import org.harper.bookstore.ui.common.SiteListRenderer;
import org.harper.frm.gui.swing.comp.table.data.TableData;

public class StockTakingFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6337181539452665666L;

	private StockTakingController controller;

	private JComboBox siteCombo;

	private ItemController<StockTakingItem> itemController;

	public StockTakingFrame() {
		super();
		setSize(800, 600);
		setTitle("Stock Taking");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);

		setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel();
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout());

		JPanel headerPanel = new JPanel();
		centerPanel.add(headerPanel, BorderLayout.NORTH);

		siteCombo = new JComboBox();
		siteCombo.setRenderer(new SiteListRenderer());
		add(siteCombo);

		// Create Table;
		itemController = new ItemController<StockTakingItem>(null,
				new TableCreator() {
					@Override
					public void createTable(JTable table) {
						// TODO Auto-generated method stub

					}
				}) {

			@Override
			protected StockTakingItem createItem(Book book) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected TableData createTableData(Object item) {
				// TODO Auto-generated method stub
				return null;
			}

		};

		setVisible(true);
	}

	public StockTakingController getController() {
		return controller;
	}

	public void setController(StockTakingController controller) {
		this.controller = controller;
	}

}
