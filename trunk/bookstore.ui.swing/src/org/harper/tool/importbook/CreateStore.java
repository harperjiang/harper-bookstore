package org.harper.tool.importbook;

import oracle.toplink.sessions.UnitOfWork;

import org.harper.bookstore.domain.store.StoreSite;
import org.harper.frm.toplink.SessionManager;

public class CreateStore {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StoreSite site1 = new StoreSite();
		site1.setName("航华仓库");
		site1.setPrivilege(0);

		StoreSite site2 = new StoreSite();
		site2.setName("家里");
		site2.setPrivilege(1);
		
//		StoreSite site3 = new StoreSite();
//		site3.setName("仓库里kmh的书");
//		site3.setPrivilege(5);

		UnitOfWork uow = SessionManager.getInstance().getSession()
				.acquireUnitOfWork();
		uow.registerNewObject(site1);
		uow.registerNewObject(site2);
//		uow.registerNewObject(site3);

		uow.commit();
	}

}
