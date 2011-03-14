package org.harper.bookstore.ui.main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.harper.frm.gui.swing.comp.window.JPowerWindow;

public class MainWindow extends JPowerWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2710860169827411879L;

	public MainWindow() {
		super();
		setTitle("Book Store Managemenet System");
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setSize(800, 600);
		
		initComponent();
	}

	protected void createMenuBar(JMenuBar menuBar) {
		JMenu systemMenu = new JMenu("System");
		menuBar.add(systemMenu);
		
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setPreferredSize(new Dimension(120,20));
		systemMenu.add(exitItem);
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MainWindow.this.dispose();
			}
		});
	}

	protected void createToolBar(JToolBar toolBar) {
		toolBar.add(new JButton("Button 1"));
	}
	
	protected void initComponent() {
		addView("Test View",new JPanel());
		addView("Test View 2",new JPanel());
	}

	public static void main(String[] args) {
		new MainWindow().setVisible(true);
	}
}
