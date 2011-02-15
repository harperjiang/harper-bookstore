package org.harper.frm.core;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;

public class BeanTester {

	PropertyDescriptor[] descs;

	Object bean;

	public BeanTester(Object bean) {
		this.bean = bean;
		descs = PropertyUtils.getPropertyDescriptors(bean);
	}

	public void test() {
		for (int i = 0; i < descs.length; i++) {
			PropertyDescriptor pd = descs[i];
			Class<?> type = pd.getPropertyType();
			if (pd.getReadMethod() == null || pd.getWriteMethod() == null)
				// Test reader/writer only
				continue;
			// Test Primary type and string type only
			Object input = null;
			if (type.isPrimitive()) {
				if (Integer.TYPE == type)
					input = 1;
				if (Boolean.TYPE == type)
					input = true;
				if (Character.TYPE == type)
					input = '1';
				if(Long.TYPE == type)
					input = 1L;
			} else if (String.class == type) {
				input = "demoString";
			} else
				continue;
			try {
				pd.getWriteMethod().invoke(bean, input);
				Object output = pd.getReadMethod()
						.invoke(bean, new Object[] {});
				Assert.assertEquals(input, output);
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("");
			}
		}
	}
}
