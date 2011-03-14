package org.harper.frm.gui.swing.print.comp;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.print.PrinterJob;

import org.harper.frm.gui.swing.print.ComponentPrintable;
import org.harper.frm.gui.swing.print.Print;
import org.harper.frm.gui.swing.print.border.LineBorder;
import org.harper.frm.gui.swing.print.layout.ExpandLayout;
import org.junit.Test;

public class TableCellTest {

	@Test
	public void testTableCell() throws Exception {
		Container container = new Container();
		container.setLayout(new ExpandLayout());
		container.setPosition(new Rectangle(0, 0, 800, 400));

		TableCell text = new TableCell(
				"This is a long text and I want to see it extend and display all the words here. If it fails you cannot see these buzz words");
		text.setFont(new Font("Arial Unicode", Font.PLAIN, 36));
		text.setPosition(new Rectangle(0, 0, 200, 10));
		text.setBorder(new LineBorder(1));
		container.add(text);

		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(container));
		if (job.printDialog())
			job.print();
	}

}
