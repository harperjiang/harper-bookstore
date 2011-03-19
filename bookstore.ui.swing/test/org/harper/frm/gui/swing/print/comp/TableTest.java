package org.harper.frm.gui.swing.print.comp;

import java.awt.Rectangle;
import java.awt.print.PrinterJob;

import org.harper.frm.gui.swing.print.ComponentPrintable;
import org.harper.frm.gui.swing.print.border.EmptyBorder;
import org.harper.frm.gui.swing.print.border.LineBorder;
import org.harper.frm.gui.swing.print.layout.FillLayout;
import org.junit.Test;

public class TableTest {

	@Test
	public void testTable() throws Exception {

		Table table = new Table(5);
		table.setPosition(new Rectangle(0, 0, 600, 0));

		table.setColumnName(new String[] { "Col 1", "Col 2", "Col 3", "Col 4",
				"Col 5" });

		table.setColumnWidth(0, 0.2f);
		table.setColumnWidth(1, 0.3f);
		table.setColumnWidth(2, 0.1f);
		table.setColumnWidth(3, 0.2f);
		table.setColumnWidth(4, 0.2f);

		table.add(new TableCell("Cell 11"));
		table.add(new TableCell("Cell 12"));
		table.add(new TableCell("Cell 13"));
		table.add(new TableCell("Cell 14"));
		table.add(new TableCell("Cell 15"));

		table.add(new TableCell("Cell 21"));
		table.add(new TableCell("Cell 22"));
		table.add(new TableCell("Cell 23"));
		table.add(new TableCell("Cell 24"));
		table.add(new TableCell("Cell 25"));

		table.add(new TableCell("Cell 31"));
		table
				.add(new TableCell(
						"Cell 32 has a lot of to say. But it doesn't know whether there's enough space. If you can read this, it means it succeed"));
		table.add(new TableCell("Cell 33"));
		table.add(new TableCell("Cell 34"));
		table.add(new TableCell("Cell 35"));

		table.setBorder(new LineBorder(1));

		Container container = new Container();
		container.setBorder(new EmptyBorder(50));
		container.setPosition(new Rectangle(0, 0, 700, 900));
		container.setLayout(new FillLayout());
		container.add(table);

		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(container));
		if (job.printDialog())
			job.print();
	}
	
	@Test
	public void testCellWrap() throws Exception {

		Table table = new Table(2);
		table.setPosition(new Rectangle(0, 0, 600, 0));

		table.setColumnName(new String[] { "Col 1","Col 2"});

		table.setColumnWidth(0, 0.2f);
		table.setColumnWidth(1, 0.8f);

		table
				.add(new TableCell(
						"Cell 32 has a lot of to say. But it doesn't know whether there's enough space. If you can read this, it means it succeed"));
		table.add(new TableCell("Cell 33"));

		table.setBorder(new LineBorder(1));

		Container container = new Container();
		container.setBorder(new EmptyBorder(50));
		container.setPosition(new Rectangle(0, 0, 700, 900));
		container.setLayout(new FillLayout());
		container.add(table);

		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(container));
		if (job.printDialog())
			job.print();
	}

}
