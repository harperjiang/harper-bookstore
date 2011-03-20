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

public class RowLayoutTest {

	@Test
	public void testLayout() throws Exception {
		Container cntr = new Container();
		cntr.setBorder(new EmptyBorder(10));
		cntr.setLayout(new RowLayout());
		cntr.setPosition(new Rectangle(0, 0, 510, 800));

		Table longTable = new Table(3);
		longTable.setColumnWidth(0, 0.2f);
		longTable.setColumnWidth(1, 0.6f);
		longTable.setColumnWidth(2, 0.2f);

		longTable.setColumnName(new String[] { "Col1", "Col2", "Col3"});
		longTable.add(new TableCell("Cell 1"));
		longTable.add(new TableCell("Cell 22"));
		longTable.add(new TableCell("Cell 333"));
//		longTable.add(new TableCell("Casdfljdaslkfjasdlkfjldka"
//				+ "sjfjweoiiowejfiaioaweapsdid"
//				+ "fdjipasdopaspdiodfjsadiopfji"
//				+ "opasdfjiosadgjiopajgiawjfgpiw"
//				+ "agjawpoijglpasjkfpiowaejipofa"
//				+ "jweoipfjhawoijfoiawhgouawiop"
//				+ "fgajeoifhdasouflsdiajflsk;adh"
//				+ "fouiasdjfiopasdjflasdjkhwrkljg"
//				+ "kjrhgioajgioergioawrhuigaweripg"
//				+ "reioghariowpjkgopewjagiowerajgi"
//				+ "oaerjgioperhgouipaerjgpiawjgoiwar"
//				+ "jtgiowreaajgoiaerjgpiaerjgoiawrjg"
//				+ "oiwjpfioweirfweopikrfopawejfgoiweaj"
//				+ "fpooaweifpoweajfoijalfijsdao;fjsado"
//				+ "lfhoweiajfawepofjasopeihfouwaejgpia"
//				+ "weijhgiwejugp0wjgp0wjtgp9iwujggawjwi"
//				+ "gjrawjgao0wrghowiaefopweiajf"));
		longTable.add(new TableCell("KK"));
		TextLabel text = new TextLabel("This is a Text Label");
		cntr.add(longTable);
		cntr.add(text);
//		cntr.setBorder(new EmptyBorder(20));

		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(new ComponentPrintable(cntr));
		if (job.printDialog())
			job.print();
	}

}
