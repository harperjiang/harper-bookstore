package org.harper.frm;

import java.text.MessageFormat;

/**
 * This class stores products and version used for printout.
 * 
 * @author Harper Jiang
 * 
 */
public class Version {

	private static final String product = Messages
			.getString("Version.productName"); //$NON-NLS-1$

	private static final String versionNumber = Messages
			.getString("Version.productVersion"); //$NON-NLS-1$

	private static final String frmVersionNumber = Messages
			.getString("Version.frmVersion"); //$NON-NLS-1$

	private static final String MSG_FORMAT = "{0} {1} (Framework Version {2})";//$NON-NLS-1$

	public static String getProduct() {
		return product;
	}

	public static String getVersionNumber() {
		return versionNumber;
	}

	public static String getFrmVersionNumber() {
		return frmVersionNumber;
	}

	public static final String getVersionInfo() {
		return MessageFormat.format(MSG_FORMAT, product, versionNumber,
				frmVersionNumber);
	}
}
