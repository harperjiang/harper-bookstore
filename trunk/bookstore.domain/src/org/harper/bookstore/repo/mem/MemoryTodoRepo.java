package org.harper.bookstore.repo.mem;

import java.util.List;

import org.harper.bookstore.domain.todo.TodoItem;
import org.harper.bookstore.domain.todo.TodoItem.Privilege;
import org.harper.bookstore.repo.TodoRepo;

public class MemoryTodoRepo implements TodoRepo {

	private MemoryCommonRepo commonRepo;

	public MemoryCommonRepo getCommonRepo() {
		return commonRepo;
	}

	public void setCommonRepo(MemoryCommonRepo commonRepo) {
		this.commonRepo = commonRepo;
	}

	@Override
	public TodoItem getTodoItem(String key) {

		for (Object obj : getCommonRepo().get(TodoItem.class)) {
			TodoItem todo = (TodoItem) obj;
			if (key.equals(todo.getKey()))
				return todo;
		}
		return null;
	}

	@Override
	public List findValidTodoItems(Privilege filter) {
		return getCommonRepo().get(TodoItem.class);
	}

}
