package org.harper.bookstore.domain.user;

import java.util.Date;

import org.harper.bookstore.domain.Entity;

public class User extends Entity {

	private String id;

	private String encryptedPass;

	private long accessControl;

	private Date lastLogin;
	
	public boolean canAccess(AccessControl function) {
		return (accessControl & (1 << function.ordinal())) != 0;
	}
}
