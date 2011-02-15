package org.harper.frm.core.module;

import org.harper.frm.core.PlatformInfo;

public class PlatformInfoMock implements PlatformInfo {

	public int currentJvmNo() {
		return 2;
	}

	public boolean inCluster() {
		return false;
	}

}
