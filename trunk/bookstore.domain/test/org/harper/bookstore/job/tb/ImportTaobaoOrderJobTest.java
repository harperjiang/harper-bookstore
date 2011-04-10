package org.harper.bookstore.job.tb;

import org.harper.bookstore.job.ImportTaobaoOrderJob;
import org.harper.bookstore.util.Utilities;
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
