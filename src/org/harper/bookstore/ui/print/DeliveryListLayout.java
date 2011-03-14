package org.harper.bookstore.ui.print;

import java.awt.Font;
import java.awt.Rectangle;

import org.harper.frm.gui.swing.print.Print;
import org.harper.frm.gui.swing.print.border.EmptyBorder;
import org.harper.frm.gui.swing.print.comp.Container;
import org.harper.frm.gui.swing.print.comp.Table;
import org.harper.frm.gui.swing.print.comp.TableCell;
import org.harper.frm.gui.swing.print.comp.TextLabel;
import org.harper.frm.gui.swing.print.layout.RowLayout;

public class DeliveryListLayout extends Container {

	private TextLabel toPerson;
	
	private TextLabel toAddress;
	
	public DeliveryListLayout() {
		super();
		setPosition(new Rectangle(0, 0, 800, 400));
		setBorder(new EmptyBorder(10));
		setLayout(new RowLayout());

		TextLabel label = new TextLabel("发货单");
		label.setFont(new Font("黑体", Font.PLAIN, 36));
		label.setAlign(Print.ALIGN_CENTER);
		add(label);
		
		Font titleFont = new Font("宋体",Font.BOLD,12);
		Font itemFont = new Font("宋体",Font.PLAIN,12);
		
		Container header = new Container();
		TextLabel toPersonLabel = new TextLabel("收货人：");
		toPersonLabel.setPosition(new Rectangle(0,0,100,20));
		toPersonLabel.setFont(titleFont);
		header.add(toPersonLabel);
		
		TextLabel toAddressLabel = new TextLabel("收货地址：");
		toAddressLabel.setPosition(new Rectangle(0,25,100,20));
		toAddressLabel.setFont(titleFont);
		header.add(toAddressLabel);
		
		toPerson = new TextLabel("");
		toPerson.setPosition(new Rectangle(170,0,300,20));
		header.add(toPerson);
		
		toAddress = new TextLabel("");
		toAddress.setPosition(new Rectangle(170,25,500,60));
		header.add(toAddress);
		
//		header.setBorder(new LineBorder(1));
		add(header);
		
		Table table = new Table(3);
		table.setPosition(new Rectangle(0,0,780,0));
		table.setColumnName(new String[]{"名称","数量","价格"});
		table.setColumnWidth(0, 0.8f);
		table.setColumnWidth(1, 0.1f);
		table.setColumnWidth(2, 0.1f);
		
		table.add(new TableCell("Book 1"));
		table.add(new TableCell("1"));
		table.add(new TableCell("128"));
		table.add(new TableCell("Book 2"));
		table.add(new TableCell("2"));
		table.add(new TableCell("128"));
		table.add(new TableCell("Book 3"));
		table.add(new TableCell("3"));
		table.add(new TableCell("128"));
		
		Container footer = new Container();
		
		add(table);
	}
}
