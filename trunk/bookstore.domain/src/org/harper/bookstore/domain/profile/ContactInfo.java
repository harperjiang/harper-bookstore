package org.harper.bookstore.domain.profile;

import org.harper.bookstore.domain.Entity;

public class ContactInfo {

	private String name;

	private String email;

	private String address;

	private String phone;

	private String mobile;

	private String postalCode;

	@Override
	public boolean equals(Object comp) {
		if (comp instanceof ContactInfo) {
			ContactInfo info = (ContactInfo) comp;
			return Entity.equals(name, info.getName())
					&& Entity.equals(email, info.getEmail())
					&& Entity.equals(address, info.getAddress())
					&& Entity.equals(phone, info.getPhone())
					&& Entity.equals(mobile, info.getMobile());
		}
		return super.equals(comp);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

}
