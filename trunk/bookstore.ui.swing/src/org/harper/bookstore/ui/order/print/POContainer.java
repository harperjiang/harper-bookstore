package org.harper.bookstore.ui.order.print;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.domain.order.OrderItem;
import org.harper.bookstore.domain.order.PurchaseOrder;

public class POContainer implements Printable {

	private PurchaseOrder order;

	public POContainer(PurchaseOrder order) {
		this.order = order;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {

		Font titleFont = new Font("Tahoma", Font.BOLD, 24);
		Font headerFont = new Font("Tahoma", Font.PLAIN, 12);
		if (0 < pageIndex)
			return NO_SUCH_PAGE;
		// pageFormat.get
		Graphics2D g2d = (Graphics2D) graphics;
		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

		g2d.setFont(titleFont);
		g2d.drawString("Distribution List", 200, 50);

		g2d.setFont(headerFont);

		// Draw table
		g2d.drawString("Order #:" + order.getNumber(), 50, 100);
		g2d.drawString("Customer:" + order.getCustomer().getId(), 350, 100);
		g2d.drawString("Creation Date:"
				+ new SimpleDateFormat("dd/MMM/yyyy").format(order
						.getCreateDate()), 50, 120);

		// Table Header
		int[] SPLIT = new int[] { 50, 140, 350, 420, 490 };

		g2d.drawRect(SPLIT[0], 150, 500, 20);
		g2d.drawLine(SPLIT[1], 150, 140, 170);
		g2d.drawLine(SPLIT[2], 150, 350, 170);
		g2d.drawLine(SPLIT[3], 150, 420, 170);
		g2d.drawLine(SPLIT[4], 150, 490, 170);

		g2d.setFont(new Font("Tahoma", Font.PLAIN, 8));
		g2d.drawString("ISBN", SPLIT[0] + 10, 165);
		g2d.drawString("Book Name", SPLIT[1] + 10, 165);
		g2d.drawString("Unit Price", SPLIT[2] + 10, 165);
		g2d.drawString("Count", SPLIT[3] + 10, 165);
		g2d.drawString("Subtotal", SPLIT[4] + 10, 165);

		int startY = 185;

		for (OrderItem item : order.getItems()) {
			g2d.drawString(item.getBook().getIsbn(), SPLIT[0] + 10, startY);
			if (!StringUtils.isEmpty(item.getBook().getName()))
				g2d.drawString(item.getBook().getName(), SPLIT[1] + 10, startY);
			g2d.drawString(item.getUnitPrice().toPlainString(), SPLIT[2] + 10,
					startY);
			g2d.drawString(String.valueOf(item.getCount()), SPLIT[3] + 10,
					startY);
			g2d.drawString(item.getUnitPrice().multiply(
					new BigDecimal(item.getCount())).toPlainString(),
					SPLIT[4] + 10, startY);
			startY += 20;
		}
		g2d.setFont(headerFont);
		g2d.drawString("Total: " + order.getTotal(), 50, startY + 20);

		return PAGE_EXISTS;
	}

}
