package org.harper.bookstore.job.tb;

import org.harper.bookstore.util.Utilities;
import org.harper.frm.job.tb.ImportTaobaoOrderJob;
import org.junit.Test;

public class ImportTaobaoOrderJobTest {

	@Test
	public void testRun() {
		ImportTaobaoOrderJob job = new ImportTaobaoOrderJob();
		job.setStart(Utilities.getBeginOfDate(1));
		job.setStop(Utilities.getEndOfDate());
		job.call();
	}

}
