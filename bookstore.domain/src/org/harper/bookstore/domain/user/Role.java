package org.harper.bookstore.domain.user;

public class Role {

	private String name;

	private long accessControl;

	public boolean canAccess(AccessControl function) {
		return (accessControl & (1 << function.ordinal())) != 0;
	}
}
