package org.harper.frm.gui;

public class MiscUtils {
	public static boolean equal(Object oldValue, Object value) {
		return (oldValue == value)
				|| (oldValue != null && oldValue.equals(value));
	}
}
