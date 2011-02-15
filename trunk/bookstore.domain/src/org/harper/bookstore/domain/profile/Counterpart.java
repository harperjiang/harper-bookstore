package org.harper.bookstore.domain.profile;

import org.harper.bookstore.domain.Entity;

public class Counterpart extends Entity {

	private int oid;

	private String id;

	private String source = Source.TAOBAO.name();

	private String name;

	private ContactInfo contact;

	public Counterpart() {
		super();
		this.contact = new ContactInfo();
	}
	
	public int getOid() {
		return oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ContactInfo getContact() {
		return contact;
	}

	public void setContact(ContactInfo contact) {
		this.contact = contact;
	}

}
