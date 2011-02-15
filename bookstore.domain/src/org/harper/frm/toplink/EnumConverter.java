package org.harper.frm.toplink;

import oracle.toplink.mappings.DatabaseMapping;
import oracle.toplink.mappings.converters.Converter;
import oracle.toplink.sessions.Session;

import org.apache.commons.lang.Validate;

public class EnumConverter implements Converter {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7909177407436957354L;

	private Class<? extends Enum> enumClass;

	private boolean useName;

	public EnumConverter(Class<? extends Enum> enumCl,boolean useName) {
		Validate.isTrue(enumCl.isEnum());
		enumClass = enumCl;
		this.useName = useName;
	}
	
	public EnumConverter(Class<? extends Enum> enumCl) {
		this(enumCl,false);
	}

	public boolean isUseName() {
		return useName;
	}

	public void setUseName(boolean useName) {
		this.useName = useName;
	}

	@Override
	public Object convertDataValueToObjectValue(Object dataValue,
			Session session) {
		if(null == dataValue)
			return null;
		return Enum.valueOf(enumClass, (String)dataValue);
	}

	@Override
	public Object convertObjectValueToDataValue(Object objectValue,
			Session session) {
		if(null == objectValue)
			return null;
		return ((Enum)objectValue).name();
	}

	@Override
	public void initialize(DatabaseMapping mapping, Session session) {
		
	}

	@Override
	public boolean isMutable() {
		return false;
	}

}
