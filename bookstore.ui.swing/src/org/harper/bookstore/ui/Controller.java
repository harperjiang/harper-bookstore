package org.harper.bookstore.ui;

import javax.swing.JComponent;

import org.harper.bookstore.repo.RepoFactory;
import org.harper.frm.gui.swing.manager.BindingManager;

public abstract class Controller {

	public RepoFactory getRepoFactory() {
		return RepoFactory.INSTANCE;
	}
	
	protected BindingManager manager;
	
	public JComponent getComponent() {
		return null;
	}
}
