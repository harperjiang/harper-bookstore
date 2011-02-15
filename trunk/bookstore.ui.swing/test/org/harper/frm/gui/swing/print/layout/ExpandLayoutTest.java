package org.harper.frm.gui.swing.print.layout;

import static org.junit.Assert.fail;

import java.awt.Rectangle;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import org.harper.frm.gui.swing.print.ComponentPrintable;
import org.harper.frm.gui.swing.print.comp.Container;
import org.harper.frm.gui.swing.print.comp.TextLabel;
import org.junit.Test;

public class ExpandLayoutTest {

	@Test
	public void testGetPreferredSize() throws PrinterException {
		Container container = new Container();
		container.setPosition(new Rectangle(0,0,100,100));
		container.setLayout(new ExpandLayout());
		
		TextLabel tl = new TextLabel("This is a text");
		tl.setPosition(new Rectangle(0,0,0,0));
		container.add(tl);
		

		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(container));
		if(job.printDialog())
			job.print();
	}

	@Test
	public void testLayout() {
		fail("Not yet implemented");
	}

}
