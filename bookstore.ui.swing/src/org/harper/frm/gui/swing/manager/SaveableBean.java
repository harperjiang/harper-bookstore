package org.harper.frm.gui.swing.manager;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.Validate;

public abstract class SaveableBean extends AbstractBean {

	public void save() {
		SaveBeanInfo info = getClass().getAnnotation(SaveBeanInfo.class);
		String propName = info.name() + ".properties";
		Properties prop = new Properties();

		saveTo(prop);

		try {
			FileOutputStream fos = new FileOutputStream(propName);
			prop.store(fos, null);
			fos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void load() {
		SaveBeanInfo info = getClass().getAnnotation(SaveBeanInfo.class);
		String propName = info.name() + ".properties";

		Properties prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(propName);
			prop.load(fis);
			fis.close();
		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		loadFrom(prop);
	}

	protected void saveTo(Properties prop) {
		try {
			BeanInfo info = Introspector.getBeanInfo(getClass());

			for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
				if (pd.getReadMethod() == null || pd.getWriteMethod() == null)
					continue;
				String key = pd.getName();
				String val = null;
				Object objval = pd.getReadMethod().invoke(this, null);
				if (String.class == pd.getPropertyType()) {
					val = (String) objval;
				} else if (pd.getPropertyType().isEnum()) {
					val = ((Enum) objval).name();
				} else {
					PropertyEditor pe = PropertyEditorManager.findEditor(pd
							.getPropertyType());
					Validate.notNull(pe);
					pe.setValue(objval);
					val = pe.getAsText();
				}
				if (null != val)
					prop.setProperty(key, val);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void loadFrom(Properties prop) {
		try {
			BeanInfo info = Introspector.getBeanInfo(getClass());

			for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
				if (pd.getReadMethod() == null || pd.getWriteMethod() == null)
					continue;
				String key = pd.getName();
				String val = prop.getProperty(key);
				Object objval = null;
				if (String.class == pd.getPropertyType()) {
					objval = val;
				}
				if (pd.getPropertyType().isEnum()) {
					if (null != val)
						objval = Enum.valueOf((Class<? extends Enum>) pd
								.getPropertyType(), val);
				} else {
					PropertyEditor pe = PropertyEditorManager.findEditor(pd
							.getPropertyType());
					Validate.notNull(pe, pd.getName() + ":"
							+ pd.getPropertyType().getName());
					pe.setAsText(val);
					objval = pe.getValue();
				}
				pd.getWriteMethod().invoke(this, objval);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
