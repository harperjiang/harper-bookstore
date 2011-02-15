package org.harper.bookstore.repo.mem;

import org.harper.bookstore.repo.CommonRepo;
import org.harper.bookstore.repo.LibraryRepo;
import org.harper.bookstore.repo.OrderRepo;
import org.harper.bookstore.repo.ProfileRepo;
import org.harper.bookstore.repo.RepoFactory;
import org.harper.bookstore.repo.StoreRepo;

public class MemoryRepoFactory implements RepoFactory {

	@Override
	public ProfileRepo getProfileRepo() {

		return new MemoryProfileRepo();

	}

	@Override
	public StoreRepo getStoreRepo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommonRepo getCommonRepo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderRepo getOrderRepo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LibraryRepo getLibraryRepo() {
		// TODO Auto-generated method stub
		return null;
	}

}
