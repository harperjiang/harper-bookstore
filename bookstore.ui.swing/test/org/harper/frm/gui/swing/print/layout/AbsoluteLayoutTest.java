package org.harper.frm.gui.swing.print.layout;

import java.awt.Rectangle;
import java.awt.print.PrinterJob;

import org.harper.frm.gui.swing.print.ComponentPrintable;
import org.harper.frm.gui.swing.print.border.EmptyBorder;
import org.harper.frm.gui.swing.print.comp.Container;
import org.harper.frm.gui.swing.print.comp.Table;
import org.harper.frm.gui.swing.print.comp.TableCell;
import org.harper.frm.gui.swing.print.comp.TextLabel;
import org.junit.Test;

public class AbsoluteLayoutTest {

	@Test
	public void testNoLayout() throws Exception {
		Container cntr = new Container();
		cntr.setBorder(new EmptyBorder(10));
		cntr.setLayout(new RowLayout());
		cntr.setPosition(new Rectangle(0, 0, 510, 800));

		Table longTable = new Table(3);
		longTable.setPosition(new Rectangle(0, 0, 510, 400));
		longTable.setColumnWidth(0, 0.2f);
		longTable.setColumnWidth(1, 0.6f);
		longTable.setColumnWidth(2, 0.2f);

		longTable.setColumnName(new String[] { "Col1", "Col2", "Col3" });
		longTable.add(new TableCell("Cell 1"));
		longTable.add(new TableCell("Cell 22"));
		longTable.add(new TableCell("Cell 333"));
		longTable.add(new TableCell("KK"));
		cntr.add(longTable);
		
		TextLabel text = new TextLabel("This is a Text Label");
		text.setPosition(new Rectangle(0, 410, 510, 400));
		
		cntr.add(text);

		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(cntr));
		if (job.printDialog())
			job.print();
	}
}
