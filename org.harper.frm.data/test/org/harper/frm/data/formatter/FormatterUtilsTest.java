package org.harper.frm.data.formatter;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.harper.frm.data.formatter.FormatterUtils;
import org.junit.Test;


public class FormatterUtilsTest {

	@Test
	public void testFormatTagNameDate() {
		String result = FormatterUtils.formatTagName(null,
				"abc_$date{yyyyMMdd}");

		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String strA = format.format(new Date());

		assertEquals("abc_" + strA, result);
	}

	@Test
	public void testFormatTagNameOffsetDate() {
		String result = FormatterUtils.formatTagName(null,
				"abc_$offsetdate{yyyyMMdd,-86400}");

		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		String strA = format.format(cal.getTime());

		assertEquals("abc_" + strA, result);
	}
}
