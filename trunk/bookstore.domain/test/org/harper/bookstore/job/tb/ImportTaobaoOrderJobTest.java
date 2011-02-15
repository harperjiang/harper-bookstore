package org.harper.bookstore.job.tb;

import org.junit.Test;

public class ImportTaobaoOrderJobTest {

	@Test
	public void testRun() {
		new ImportTaobaoOrderJob().run();
	}

}
