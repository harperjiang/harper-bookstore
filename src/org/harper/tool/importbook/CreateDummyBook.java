package org.harper.tool.importbook;

import oracle.toplink.sessions.UnitOfWork;

import org.harper.bookstore.domain.profile.Book;
import org.harper.frm.toplink.SessionManager;

public class CreateDummyBook {

	public static void main(String[] args) {
		Book dummy1 = new Book();
		dummy1.setIsbn("0001");
		dummy1.setName("Dummy Book 1");
		
		Book dummy2 = new Book();
		dummy2.setIsbn("0002");
		dummy2.setName("Dummy Book 2");
		
UnitOfWork uow = SessionManager.getInstance().getSession().acquireUnitOfWork();
		
		uow.registerNewObject(dummy1);
		uow.registerNewObject(dummy2);
		
		uow.commit();
	}
}
