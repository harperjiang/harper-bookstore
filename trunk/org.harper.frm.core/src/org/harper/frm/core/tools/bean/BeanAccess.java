package org.harper.frm.core.tools.bean;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.harper.frm.core.logging.LogManager;


//import java.util.HashMap;
/**
 * Provide Bean-Related access utilities(getter/setter,copy etc.).
 * 
 * @author Harper
 * @version 1.1
 * @since IPS 1.0
 */
public abstract class BeanAccess {

	private static final String BEANACCESS_CLASSNAME = "beanAccess.className";

	private static BeanAccess instance;

	public static BeanAccess getInstance() {
		Log log = LogManager.getInstance().getLogger(BeanAccess.class);
		if (instance == null) {
			try {
				Properties prop = new Properties();
				prop.load(Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("ivofrm_config.properties"));
				String className = (String) prop.get(BEANACCESS_CLASSNAME);
				instance = (BeanAccess) Class.forName(className).newInstance();
				log.info("Use BeanAccess " + className);
			} catch (Exception e) {
				log.info("Exception when loading BeanAccess Implementation");
				instance = new BeanUtilBeanAccess();
				log.info("Use Default BeanUtilBeanAccess");
			}
		}
		return instance;
	}

	/**
	 * Set the bean field indicated by accessStr via "set" Method.
	 * 
	 * @since 1.0
	 * @param accessStr
	 * @param src
	 * @param value
	 */
	public abstract void set(String accessStr, Object src, Object value);

	/**
	 * @since 1.0
	 * @param accessStr
	 * @param src
	 * @return
	 */
	public abstract Object get(String accessStr, Object src);

	/**
	 * Extract attributes from a collection
	 * 
	 * @param source
	 * @param field
	 * @return
	 */
	public List<? extends Object> extract(List<? extends Object> source,
			String field) {
		ArrayList<Object> result = new ArrayList<Object>();
		for (Object next : source)
			try {
				result.add(get(field, next));
			} catch (Exception e) {
				e.printStackTrace();
			}
		return result;
	}

	public <T> List<T> wrap(List<? extends Object> source, Class<T> wrapClass,
			Class<? extends Object> argType) {
		ArrayList<T> result = new ArrayList<T>();
		if (null == source || source.size() == 0)
			return result;
		try {
			Constructor<T> con = wrapClass.getConstructor(argType);
			for (Object src : source) {
				result.add(con.newInstance(src));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	/**
	 * Implemented by Subclasses;
	 * 
	 * @param from
	 * @param to
	 */
	public void copy(Object from, Object to) {

	}

}
