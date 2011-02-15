package org.harper.frm.gui.swing.print.comp;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.print.PrinterJob;

import org.harper.frm.gui.swing.print.ComponentPrintable;
import org.harper.frm.gui.swing.print.Print;
import org.harper.frm.gui.swing.print.comp.TextLabel;
import org.junit.Test;

public class TextLabelTest {

	@Test
	public void testNormalPrint() throws Exception {
		TextLabel label = new TextLabel("This is a text");
		label.setPosition(new Rectangle(10, 10, 300, 50));

		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(label));
		if (job.printDialog())
			job.print();
	}

	@Test
	public void testChangeRow() throws Exception {
		TextLabel label = new TextLabel(
				"The quick brown fox jumps over the lazy dog.The quick brown fox jumps over the lazy dog.");
		label.setPosition(new Rectangle(10, 10, 300, 50));
		label.setFont(new Font("SimSun", Font.BOLD, 12));
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(label));
		if (job.printDialog())
			job.print();
	}

	@Test
	public void testAlign() throws Exception {
		TextLabel label = new TextLabel("This is a centered Text");
		label.setAlign(Print.ALIGN_TOP | Print.ALIGN_CENTER);
		label.setPosition(new Rectangle(10, 10, 400, 50));

		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(label));
		if (job.printDialog())
			job.print();
	}
}
