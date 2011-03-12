package org.harper.bookstore.repo.toplink;

import org.harper.bookstore.repo.CommonRepo;
import org.harper.bookstore.repo.LibraryRepo;
import org.harper.bookstore.repo.OrderRepo;
import org.harper.bookstore.repo.ProfileRepo;
import org.harper.bookstore.repo.RepoFactory;
import org.harper.bookstore.repo.StoreRepo;
import org.harper.bookstore.repo.TodoRepo;

public class TopLinkRepoFactory implements RepoFactory {

	@Override
	public ProfileRepo getProfileRepo() {
		return new TopLinkProfileRepo();
	}

	private StoreRepo storeRepo;

	private CommonRepo commonRepo;

	private TodoRepo todoRepo;

	@Override
	public StoreRepo getStoreRepo() {
		if (null == storeRepo)
			storeRepo = new TopLinkStoreRepo();
		return storeRepo;
	}

	@Override
	public CommonRepo getCommonRepo() {
		if (null == commonRepo)
			commonRepo = new TopLinkCommonRepo();
		return commonRepo;
	}

	@Override
	public OrderRepo getOrderRepo() {
		return new TopLinkOrderRepo();
	}

	@Override
	public LibraryRepo getLibraryRepo() {
		return new TopLinkLibraryRepo();
	}

	@Override
	public TodoRepo getTodoRepo() {
		if (null == todoRepo)
			todoRepo = new TopLinkTodoRepo();
		return todoRepo;
	}

}
