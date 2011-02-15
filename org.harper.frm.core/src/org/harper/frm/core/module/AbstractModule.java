package org.harper.frm.core.module;

import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.harper.frm.core.ServiceRegistry;
import org.harper.frm.core.logging.LogManager;


/**
 * An implementation of <code>Module</code>
 * 
 * @author Harper Jiang
 * @version 1.0
 */
public abstract class AbstractModule implements Module {

	private List<Module> depends;

	private transient boolean inited = false;

	private transient int jvmNo = 1;

	private String name;

	public AbstractModule() {
		super();
		setDepends(new Vector<Module>());
		setName(getClass().getName());
	}

	public AbstractModule(String name) {
		super();
		setName(name);
		setDepends(new Vector<Module>());
	}

	public List<Module> getDepends() {
		return depends;
	}

	public void setDepends(List<Module> newDepends) {
		this.depends = newDepends;
	}

	public synchronized void initialize() throws ModuleInitException {
		if (!inited) {
			if (getDepends() != null)
				for (Module module : getDepends())
					module.initialize();

			// Check whether all given ancestor had been inited.
			if (getDepends() != null)
				for (Module module : getDepends())
					if (!module.isInitialized())
						throw new ModuleInitException("Ancestor Module "
								+ module.getName()
								+ " cannot be started. Module " + getName()
								+ " initialization failed.");
			initModule();
			inited = true;
			LogManager.getInstance().getLogger(getClass()).info(
					"Module " + getName() + " initialized.");
		}
	}

	public synchronized void destroy() {
		if (inited) {
			destroyModule();
			inited = false;
			LogManager.getInstance().getLogger(getClass()).info(
					"Module " + getName() + " destroyed.");
			if (getDepends() != null)
				for (Module module : getDepends())
					module.destroy();
		}
	}

	/**
	 * Subclasses should implement this method to init the corresponding module
	 * 
	 * @throws ModuleInitException
	 */
	protected abstract void initModule() throws ModuleInitException;

	/**
	 * Subclasses can override this method to perform clearup work.
	 */
	protected void destroyModule() {

	}

	public boolean isInitialized() {
		return inited;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getJvmNo() {
		return jvmNo;
	}

	protected void setJvmNo(int jvmNo) {
		this.jvmNo = jvmNo;
	}

	public void registerService(String name, Object service) {
		ServiceRegistry.register(name, service);
	}

	public void unregisterService(String name) {
		ServiceRegistry.unregister(name);
	}

	public void registerService(String name, Object service, Properties selector) {
		registerService(name, service);
	}

	//
	/**
	 * Convenient Method to get service by the given class's simple name.
	 * 
	 * @param <T>
	 * @param serviceClass
	 * @return the first service that was found.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getService(Class<T> serviceClass) {
		return (T) getService(serviceClass.getName());
	}

	/**
	 * Convenient Method to get service by the given name.
	 * 
	 * @param name
	 *            Service Name
	 * @return the first service that was found.
	 */
	public Object getService(String name) {
		return ServiceRegistry.getService(name);
	}

	/**
	 * Convenient Method to get service by the given class's simple name and
	 * selector.
	 * 
	 * @param <T>
	 * @param serviceClass
	 * @param selector
	 * @return The first Service that was found
	 */
	@SuppressWarnings("unchecked")
	public <T> T getService(Class<T> serviceClass, String selector) {
		return (T) getService(serviceClass.getName(), selector);
	}

	/**
	 * Convenient Method to get service by the given name and selector.
	 * 
	 * @param name
	 *            Service Name
	 * @param selector
	 * @return The first Service that was found
	 */
	public Object getService(String name, String selector) {
		return getService(name);
	}
}
