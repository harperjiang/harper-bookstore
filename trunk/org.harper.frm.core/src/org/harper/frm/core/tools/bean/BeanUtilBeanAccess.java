package org.harper.frm.core.tools.bean;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;

public class BeanUtilBeanAccess extends BeanAccess {

    public Object get(String accessStr, Object src) {
        try {
            return PropertyUtils.getProperty(src, accessStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void set(String accessStr, Object src, Object value) {
        try {
            if (value instanceof String
                    && String.class != PropertyUtils.getPropertyDescriptor(src,
                            accessStr).getPropertyType()) {
                BeanUtilsBean.getInstance().setProperty(src, accessStr, value);
            } else {
                PropertyUtils.setProperty(src, accessStr, value);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
