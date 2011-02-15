package org.harper.frm.core.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotations to declare a config bean name.
 * 
 * @author Harper Jiang
 * @version 1.0
 * @since com.oocl.frm.core 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConfigBeanInfo {
	String name();

	String module() default ("undefined");
}
