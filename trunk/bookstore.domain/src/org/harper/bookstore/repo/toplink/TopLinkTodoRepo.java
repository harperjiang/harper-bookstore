package org.harper.bookstore.repo.toplink;

import java.util.List;

import org.harper.bookstore.domain.todo.TodoItem;
import org.harper.bookstore.domain.todo.TodoItem.Privilege;
import org.harper.bookstore.repo.TodoRepo;

public class TopLinkTodoRepo extends TopLinkRepo implements TodoRepo {

	@Override
	public TodoItem getTodoItem(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TodoItem> findValidTodoItems(Privilege filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
