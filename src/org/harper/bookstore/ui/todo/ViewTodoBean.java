package org.harper.bookstore.ui.todo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.harper.bookstore.domain.todo.TodoItem;
import org.harper.bookstore.domain.todo.TodoItem.Privilege;
import org.harper.frm.gui.swing.manager.AbstractBean;

public class ViewTodoBean extends AbstractBean {

	private Map<TodoItem.Privilege, List<TodoItem>> todoItems = new HashMap<Privilege, List<TodoItem>>();

	private TodoItem selected = new TodoItem();

	public Map<TodoItem.Privilege, List<TodoItem>> getTodoItems() {
		return todoItems;
	}

	public void setTodoItems(Map<TodoItem.Privilege, List<TodoItem>> todoItems) {
		Map<TodoItem.Privilege, List<TodoItem>> old = getTodoItems();
		this.todoItems = todoItems;
		firePropertyChange("todoItems", old, todoItems);
	}

	public TodoItem getSelected() {
		return selected;
	}

	public void setSelected(TodoItem selected) {
		TodoItem old = getSelected();
		this.selected = selected;
		firePropertyChange("selected", old, selected);
	}

}
