package org.harper.frm.gui.swing.print.comp;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.print.PrinterJob;

import org.harper.frm.gui.swing.print.ComponentPrintable;
import org.harper.frm.gui.swing.print.Print;
import org.harper.frm.gui.swing.print.comp.Container;
import org.harper.frm.gui.swing.print.comp.TextLabel;
import org.junit.Test;

public class ContainerTest {

	@Test
	public void testDraw() throws Exception {
		Container container = new Container();
		container.setPosition(new Rectangle(0,0,800,400));
		
		TextLabel text = new TextLabel("发货单");
		text.setAlign(Print.ALIGN_CENTER);
		text.setFont(new Font("Arial Unicode",Font.PLAIN,36));
		text.setPosition(new Rectangle(0,0,800,100));
		container.add(text);
		
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(container));
		if(job.printDialog())
			job.print();
	}

}
