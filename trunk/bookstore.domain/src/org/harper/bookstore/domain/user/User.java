package org.harper.bookstore.domain.user;

import java.util.Date;

import org.harper.bookstore.domain.Entity;

public class User extends Entity {

	private String id;

	private String encryptedPass;

	private Role role;

	private Date lastLoginDate;

	public boolean canAccess(AccessControl function) {
		return getRole().canAccess(function);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEncryptedPass() {
		return encryptedPass;
	}

	public void setEncryptedPass(String encryptedPass) {
		this.encryptedPass = encryptedPass;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

}
