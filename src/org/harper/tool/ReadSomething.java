package org.harper.tool;

import oracle.toplink.sessions.Session;
import oracle.toplink.sessions.UnitOfWork;

import org.harper.bookstore.domain.profile.Book;
import org.harper.frm.toplink.SessionManager;

public class ReadSomething {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Session ssn = SessionManager.getInstance().getSession();
		ssn.readAllObjects(Book.class);
		UnitOfWork uow = ssn.acquireUnitOfWork();
//		Book book = (Book)uow.readObject(Book.class,
//				new ExpressionBuilder().get("isbn").equal("9780316040501"));
//		System.out.println(book.getName());
		
		Book book1 = new Book();
		book1.setIsbn("9780316040501");
		book1.setName("My Very Fairy Princess");	
		uow.mergeClone(book1);
		uow.commit();
	}

}
