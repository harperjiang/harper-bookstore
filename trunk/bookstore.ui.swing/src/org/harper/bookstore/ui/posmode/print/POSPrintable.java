package org.harper.bookstore.ui.posmode.print;

import java.awt.Font;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.harper.bookstore.ui.posmode.POSBookItem;
import org.harper.bookstore.ui.posmode.POSModeBean;
import org.harper.frm.gui.swing.print.Print;
import org.harper.frm.gui.swing.print.border.EmptyBorder;
import org.harper.frm.gui.swing.print.comp.Container;
import org.harper.frm.gui.swing.print.comp.Table;
import org.harper.frm.gui.swing.print.comp.TableCell;
import org.harper.frm.gui.swing.print.comp.TextLabel;
import org.harper.frm.gui.swing.print.layout.RowLayout;

public class POSPrintable extends Container {

	private TextLabel titleLabel;

	private TextLabel numberLabel;

	private Table contentTable;

	private TextLabel dateLabel;

	public POSPrintable() {
		setLayout(new RowLayout());
		Font titleFont = new Font("微软雅黑", Font.BOLD, 12);
		Font normalFont = new Font("微软雅黑", Font.PLAIN, 8);

		titleLabel = new TextLabel("发货扫描清单");
		titleLabel.setAlign(Print.ALIGN_CENTER);
		titleLabel.setBorder(new EmptyBorder(0, 0, 5, 0));
		titleLabel.setFont(titleFont);
		add(titleLabel);

		numberLabel = new TextLabel();
		numberLabel.setAlign(Print.ALIGN_LEFT);
		numberLabel.setFont(normalFont);
		add(numberLabel);

		contentTable = new Table(3);
		contentTable.setFont(normalFont);
		contentTable.setColumnName(new String[] { "ISBN", "书名", "数量" });
		contentTable.setColumnWidth(0, 0.3f);
		contentTable.setColumnWidth(1, 0.5f);
		contentTable.setColumnWidth(2, 0.2f);
		add(contentTable);

		dateLabel = new TextLabel();
		dateLabel.setFont(normalFont);
		add(dateLabel);

		setPosition(new Rectangle(0, 0, 500, 310));
//		setBorder(new EmptyBorder(10));
	}

	public TextLabel getTitleLabel() {
		return titleLabel;
	}

	public TextLabel getNumberLabel() {
		return numberLabel;
	}

	public Table getContentTable() {
		return contentTable;
	}

	public TextLabel getDateLabel() {
		return dateLabel;
	}

	public void setSource(POSModeBean s) {
		getNumberLabel().setText("订单号:" + s.getTradeId());
		getDateLabel().setText(
				"打印日期:"
						+ new SimpleDateFormat("yyyy-MM-dd HH:mm")
								.format(new Date()));
		for (POSBookItem item : s.getItems()) {
			getContentTable().add(new TableCell(item.getBook().getIsbn()));
			getContentTable().add(new TableCell(item.getBook().getName()));
			getContentTable().add(new TableCell(item.getCount()));
		}
	}

}
