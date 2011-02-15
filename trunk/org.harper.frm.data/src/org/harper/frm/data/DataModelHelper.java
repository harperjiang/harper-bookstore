package org.harper.frm.data;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;

/**
 * 
 * @author Harper Jiang
 * @since ips.frm.component 1.0
 * @version 1.0 Jun 16, 2009
 */
public class DataModelHelper {

	/**
	 * 
	 * @param columnCount
	 * @return
	 */
	public static String[] initColumnNames(int columnCount) {
		ArrayList<String> columnNames = new ArrayList<String>();
		for (int i = 0; i < columnCount; i++) {
			if (i < 26)
				columnNames.add(new String(new char[] { (char) ('A' + i) }));
			else {
				StringBuilder newname = new StringBuilder();
				String last = columnNames.get(i - 1);
				char[] chars = last.toCharArray();
				int count = chars.length - 1;
				for (count = chars.length - 1; count >= 0; count--)
					if (chars[count] < 'Z') {
						chars[count] += 1;
						break;
					} else {
						chars[count] = 'A';
					}
				if (count == -1)
					newname.append('A');
				newname.append(chars);
				columnNames.add(newname.toString());
			}
		}
		String[] result = new String[columnNames.size()];
		return columnNames.toArray(result);
	}

	/**
	 * 
	 * @param buffer
	 * @throws Exception
	 */
	public static void clean(final Object buffer) {
//		AccessController.doPrivileged(new PrivilegedAction<Object>() {
//			public Object run() {
//				try {
//					Method getCleanerMethod = buffer.getClass().getMethod(
//							"cleaner", new Class[0]);
//					getCleanerMethod.setAccessible(true);
//					sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod
//							.invoke(buffer, new Object[0]);
//					cleaner.clean();
//				} catch (Exception e) {
//					throw new RuntimeException(e);
//				}
//				return null;
//			}
//		});
	}

}
