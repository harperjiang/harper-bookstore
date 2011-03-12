package org.harper.bookstore.ui.todo;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

import org.harper.bookstore.domain.todo.TodoItem;
import org.harper.bookstore.domain.todo.TodoItem.Privilege;
import org.harper.frm.gui.swing.comp.tree.TwoLevelTreeModel;

public class ViewTodoFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -218321551551584110L;

	private ViewTodoController controller;

	public ViewTodoController getController() {
		return controller;
	}

	public void setController(ViewTodoController controller) {
		this.controller = controller;
	}

	private JTree todoTree;

	private JTextArea contentArea;

	public ViewTodoFrame() {
		setTitle("View Todo");
		setSize(250, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		add(splitPane);

		todoTree = new JTree();

		TwoLevelTreeModel<Privilege, TodoItem> tm = new TwoLevelTreeModel<Privilege, TodoItem>(
				Privilege.class, TodoItem.class);
		todoTree.setModel(tm);
		todoTree.setRootVisible(false);
		todoTree.setCellRenderer(new PrivilegeTreeCellRenderer());
		todoTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);

		todoTree.getSelectionModel().addTreeSelectionListener(
				new TreeSelectionListener() {
					@Override
					public void valueChanged(TreeSelectionEvent e) {
						Object last = getTodoTree().getSelectionPath()
								.getLastPathComponent();
						if (last instanceof TodoItem) {
							getController().getBean().setSelected(
									(TodoItem) last);
						} else {
							getController().getBean().setSelected(null);
						}
					}
				});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(todoTree);
		splitPane.setLeftComponent(scrollPane);

		contentArea = new JTextArea();
		contentArea.setEditable(false);
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setViewportView(contentArea);
		splitPane.setRightComponent(scrollPane2);

		splitPane.setDividerLocation(0.5);

		setVisible(true);
	}

	public JTree getTodoTree() {
		return todoTree;
	}

	public JTextArea getContentArea() {
		return contentArea;
	}

}
