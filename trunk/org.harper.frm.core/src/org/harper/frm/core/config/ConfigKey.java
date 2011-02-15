package org.harper.frm.core.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotations to declares a config key.
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since Core 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigKey {
	String value();
}
