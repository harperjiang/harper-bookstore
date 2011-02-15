package org.harper.frm.core.config;

import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.FactoryBean;

/**
 * A Spring Bean that load Config value from ConfigManager and use in Spring
 * 
 * @author Harper Jiang
 * 
 * @version 1.0 Mar 4, 2010
 */
public class ConfigValueBean implements FactoryBean {

    private String configKey;

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public Object getObject() throws Exception {
        Validate.notNull(getConfigKey());
        return ConfigManager.getInstance().getConfigValue(getConfigKey());
    }

    public Class getObjectType() {
        return String.class;
    }

    public boolean isSingleton() {
        return false;
    }

}
