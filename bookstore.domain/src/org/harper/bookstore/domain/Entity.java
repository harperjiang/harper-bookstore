package org.harper.bookstore.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.harper.frm.core.IAdaptor;

public abstract class Entity implements IAdaptor {

	private PropertyChangeSupport support;

	private int oid;

	private int version;

	private boolean valid;

	private String creator;

	public Entity() {
		super();

		support = new PropertyChangeSupport(this);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	@Override
	public Object getAdaptor(Class<?> adapterClass) {
		if (PropertyChangeSupport.class == adapterClass)
			return support;
		return null;
	}

	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Entity) {
			return getClass().equals(obj.getClass())
					&& getOid() == ((Entity) obj).getOid()
					&& getVersion() == ((Entity) obj).getVersion();
		}
		return super.equals(obj);
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public static boolean equals(Object a, Object b) {
		if (a == b)
			return true;
		if (a == null)
			return false;
		return a.equals(b);
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

}
