package org.harper.bookstore.repo;

import org.harper.bookstore.repo.toplink.TopLinkRepoFactory;



public interface RepoFactory {

	public CommonRepo getCommonRepo();
	
	public ProfileRepo getProfileRepo();
	
	public StoreRepo getStoreRepo();
	
	public OrderRepo getOrderRepo();
	
	public LibraryRepo getLibraryRepo();
	
	public TodoRepo getTodoRepo();
	
	public static RepoFactory INSTANCE = new TopLinkRepoFactory();
	
//	public static RepoFactory INSTANCE = new MemoryRepoFactory();
}
