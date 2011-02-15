package org.harper.frm.core.module;

import org.harper.frm.core.module.AbstractModule;
import org.harper.frm.core.module.ModuleInitException;


public class ErrorModule extends AbstractModule {

	public ErrorModule() {
		super();
	}

	@Override
	protected void initModule() throws ModuleInitException {
		throw new ModuleInitException();
	}

}
