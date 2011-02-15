package org.harper.frm.core.module;

import java.util.List;

/**
 * 
 * The interface <code>Module</code> provides the functions required by
 * intermodular dependency control.
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since Core 1.0
 */
public interface Module {

	/**
	 * 
	 * @return whether the module is inited.
	 */
	public boolean isInitialized();

	/**
	 * 
	 * @return the module name
	 */
	public String getName();

	/**
	 * Initialize the given module. This will trigger the initialization of all
	 * depending modules.
	 * 
	 * @throws ModuleInitException
	 */
	public void initialize() throws ModuleInitException;

	/**
	 * Destroy the given module.
	 */
	public void destroy();

	/**
	 * Get Dependency of the current module
	 * 
	 * @return the modules that this one depends on
	 */
	public List<Module> getDepends();

}
