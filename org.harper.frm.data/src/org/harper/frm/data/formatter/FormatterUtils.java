package org.harper.frm.data.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;

/**
 * Supporting use a string including date pattern to describe dynamic file
 * names. For example, if the file name comprise of a constant prefix and
 * current date with format "YYYY-MM-dd HH24:mm", the pattern is like the
 * following : $name_$date{YYYY-MM-dd HH24:mm}
 * 
 * @author Harper Jiang
 * @since com.oocl.frm.data 1.0
 * @version 1.0 Dec 28, 2009
 */
public class FormatterUtils {

	protected static final String DATE_PATTERN_ALL = ".*\\$date\\{([A-Za-z0-9:\\-\\s\\_]+)\\}.*";

	protected static final String OFFSET_DATE_PATTERN_ALL = ".*\\$offsetdate\\{([A-Za-z0-9:\\-\\s\\_]+)\\,([0-9\\-]+)\\}.*";

	protected static final String DATE_PATTERN_REP = "\\$date\\{([A-Za-z0-9:\\-\\s\\_]+)\\}";

	protected static final String OFFSET_DATE_PATTERN_REP = "\\$offsetdate\\{([A-Za-z0-9:\\-\\s\\_]+)\\,([0-9\\-]+)\\}";

	public static final String TAG_NAME = "\\$name";

	public static final String TAG_DATE = "\\$date";

	public static final String TAG_OFFSETDATE = "\\$offsetdate";

	public static String formatTagName(String name, String pattern) {

		if (!StringUtils.isEmpty(pattern)) {
			try {
				String fileName = pattern;

				if (null != name)
					fileName = fileName.replaceFirst(TAG_NAME, name);

				Pattern pt = Pattern.compile(DATE_PATTERN_ALL);
				Matcher matcher = pt.matcher(pattern);
				if (matcher.matches()) {
					String datePattern = matcher.group(1);
					String dateString = new SimpleDateFormat(datePattern)
							.format(new Date());
					fileName = fileName.replaceFirst(DATE_PATTERN_REP,
							dateString);
				}

				pt = Pattern.compile(OFFSET_DATE_PATTERN_ALL);
				matcher = pt.matcher(fileName);
				if (matcher.matches()) {
					String datePattern = matcher.group(1);
					long offset = Long.parseLong(matcher.group(2));
					String dateString = new SimpleDateFormat(datePattern)
							.format(new Date(System.currentTimeMillis()
									+ offset * 1000));
					fileName = fileName.replaceFirst(OFFSET_DATE_PATTERN_REP,
							dateString);
				}

				return fileName;
			} catch (Exception e) {
				LogFactory.getLog(FormatterUtils.class).warn(
						"Cannot parse the pattern " + pattern, e);
				return null;
			}
		} else {
			return name;
		}
	}
}
