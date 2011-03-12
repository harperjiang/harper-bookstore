package org.harper.bookstore.repo;

import java.util.List;

import org.harper.bookstore.domain.todo.TodoItem;
import org.harper.bookstore.domain.todo.TodoItem.Privilege;

public interface TodoRepo {

	public TodoItem getTodoItem(String key);
	
	public List<TodoItem> findValidTodoItems(Privilege filter);
	
}
