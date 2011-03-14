package org.harper.frm.gui.swing.comp.window;

import java.awt.BorderLayout;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

public class JPowerWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4108750570766579528L;

	private JToolBar toolBar;

	private JSplitPane splitPane;

	private JDesktopPane editorPane;

	private JTabbedPane viewPane;

	public JPowerWindow() {
		super();

		setLayout(new BorderLayout());
		toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);
		createToolBar();

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		add(splitPane, BorderLayout.CENTER);

		viewPane = new JTabbedPane();
		splitPane.setLeftComponent(viewPane);

		editorPane = new JDesktopPane();
		splitPane.setRightComponent(editorPane);

		splitPane.setDividerLocation(150);
	}

	protected void createToolBar() {
		
	}

	public void addView(String name, JPanel newView) {
		
	}
	
	public void addEditor(JInternalFrame editor) {
	
	}

	public static void main(String[] args) {
		new JPowerWindow().setVisible(true);
	}
}
