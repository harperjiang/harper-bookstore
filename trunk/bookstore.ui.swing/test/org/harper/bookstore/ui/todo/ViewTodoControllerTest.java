package org.harper.bookstore.ui.todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.harper.bookstore.domain.todo.TodoItem;
import org.harper.bookstore.domain.todo.TodoItem.Privilege;

public class ViewTodoControllerTest {

	public static void main(String[] args) throws Exception {
		final ViewTodoController controller = new ViewTodoController();

		Thread.sleep(3000);
		final Map<Privilege, List<TodoItem>> newMap = new HashMap<Privilege, List<TodoItem>>();

		for (int j = 0; j < 5; j++) {
			Privilege p = Privilege.values()[j];
			ArrayList<TodoItem> urgent = new ArrayList<TodoItem>();
			for (int i = 0; i < 4; i++) {
				TodoItem item = new TodoItem();
				item.setOid(j * 100 + i);
				item.setPrivilege(Privilege.values()[j]);
				item.setSubject(item.getPrivilege().name() + " Subject " + i);
				item.setContent(item.getPrivilege().name() + " Content " + i);
				urgent.add(item);
			}
			newMap.put(p, urgent);
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				controller.getBean().setTodoItems(newMap);
			}
		});
	}

}
