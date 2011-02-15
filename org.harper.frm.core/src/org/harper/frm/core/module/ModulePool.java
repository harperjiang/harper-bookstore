package org.harper.frm.core.module;

import java.util.HashMap;

import org.apache.commons.lang.Validate;

public class ModulePool {

	private HashMap<String, AbstractModule> modules;

	public ModulePool() {
		modules = new HashMap<String, AbstractModule>();
	}

	public Module getModule(String moduleName) {
		return getModules().get(moduleName);
	}

	public HashMap<String, Module> getModules() {
		return (HashMap<String, Module>) modules.clone();
	}

	public void setModules(HashMap<String, AbstractModule> newModules) {
		Validate.notNull(newModules);
		this.modules.clear();
		this.modules.putAll(newModules);
	}

	public void addModule(AbstractModule newModule) {
		this.modules.put(newModule.getName(), newModule);
	}

	public void initModules() throws ModuleInitException {
		for (Module module : modules.values())
			module.initialize();
	}

	public void destroyModules() {
		for (Module module : modules.values())
			module.destroy();
	}
}
