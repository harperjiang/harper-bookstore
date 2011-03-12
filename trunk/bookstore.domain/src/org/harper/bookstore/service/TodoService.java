package org.harper.bookstore.service;

import java.util.List;

import org.harper.bookstore.domain.todo.TodoItem;
import org.harper.bookstore.domain.todo.TodoItem.Privilege;
import org.harper.bookstore.repo.TodoRepo;

public class TodoService extends Service {

	private TodoRepo todoRepo;

	public TodoRepo getTodoRepo() {
		if (null == todoRepo)
			todoRepo = getRepoFactory().getTodoRepo();
		return todoRepo;
	}

	public void setTodoRepo(TodoRepo todoRepo) {
		this.todoRepo = todoRepo;
	}

	public List<TodoItem> getValidTodoItems() {
		startTransaction();
		try {
			return getTodoRepo().findValidTodoItems(Privilege.REFERENCE);
		} finally {
			releaseTransaction();
		}
	}
}
