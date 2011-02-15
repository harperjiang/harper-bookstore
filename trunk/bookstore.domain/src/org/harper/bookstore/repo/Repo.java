package org.harper.bookstore.repo;

public interface Repo {
	public <T> T readObject(Class<T> clazz, int oid);
}
