package org.harper.bookstore.ui.main;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.harper.bookstore.ui.Controller;

public class ControllerAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5825018566654434068L;
	private Class<? extends Controller> controller;

	public ControllerAction(String name, Class<? extends Controller> ctrl) {
		super(name);
		this.controller = ctrl;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			this.controller.newInstance();
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		} finally {

		}
	}

}
