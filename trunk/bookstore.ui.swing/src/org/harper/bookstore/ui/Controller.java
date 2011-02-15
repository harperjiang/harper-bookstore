package org.harper.bookstore.ui;

import org.harper.bookstore.repo.RepoFactory;
import org.harper.frm.gui.swing.manager.BindingManager;

public class Controller {

	public RepoFactory getRepoFactory() {
		return RepoFactory.INSTANCE;
	}
	
	protected BindingManager manager;
}
