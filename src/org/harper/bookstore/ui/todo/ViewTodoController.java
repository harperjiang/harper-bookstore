package org.harper.bookstore.ui.todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.harper.bookstore.domain.todo.TodoItem;
import org.harper.bookstore.domain.todo.TodoItem.Privilege;
import org.harper.bookstore.service.TodoService;
import org.harper.bookstore.ui.Controller;
import org.harper.frm.gui.swing.comp.tree.TwoLevelTreeBinding;
import org.harper.frm.gui.swing.manager.BindingManager;
import org.harper.frm.gui.swing.manager.JTextBinding;

public class ViewTodoController extends Controller {

	private ViewTodoFrame frame;

	private ViewTodoBean bean;

	private TodoService todoService;

	public ViewTodoController() {
		super();

		frame = new ViewTodoFrame();
		frame.setController(this);

		bean = new ViewTodoBean();
		initManager();

		loadBean();
	}

	protected void initManager() {
		manager = new BindingManager(bean);

		manager.addBinding(new TwoLevelTreeBinding(frame.getTodoTree(),
				"todoItems"));
		manager.addBinding(new JTextBinding(frame.getContentArea(),
				"selected.content"));
		manager.loadAll();
	}

	protected void loadBean() {
		new Thread() {
			public void run() {

				final List<TodoItem> items = getTodoService()
						.getValidTodoItems();

				// Compose Map
				final Map<Privilege, List<TodoItem>> itemMap = new HashMap<Privilege, List<TodoItem>>();

				for (TodoItem item : items) {
					if (!itemMap.containsKey(item.getPrivilege())) {
						itemMap.put(item.getPrivilege(),
								new ArrayList<TodoItem>());
					}
					itemMap.get(item.getPrivilege()).add(item);
				}

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						bean.setTodoItems(itemMap);
						if (!items.isEmpty())
							bean.setSelected(items.get(0));
					}
				});
			}
		}.start();
	}

	public TodoService getTodoService() {
		if (null == todoService)
			todoService = new TodoService();
		return todoService;
	}

	public void setTodoService(TodoService todoService) {
		this.todoService = todoService;
	}

	public ViewTodoFrame getFrame() {
		return frame;
	}

	public ViewTodoBean getBean() {
		return bean;
	}

	public static void main(String[] args) {
		new ViewTodoController();
	}
}
