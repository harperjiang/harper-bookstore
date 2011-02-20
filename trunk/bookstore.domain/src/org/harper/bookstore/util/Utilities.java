package org.harper.bookstore.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class Utilities {

	public static Date getBeginOfDate(int prev) {
		Date stopDate = new Date();
		stopDate = DateUtils.addDays(
				DateUtils.truncate(stopDate, Calendar.DATE), -prev);
		return stopDate;
	}

	public static Date getEndOfDate() {
		Date stopDate = new Date();
		stopDate = DateUtils.addSeconds(DateUtils.addDays(
				DateUtils.truncate(stopDate, Calendar.DATE), 1), -1);
		return stopDate;
	}
}
