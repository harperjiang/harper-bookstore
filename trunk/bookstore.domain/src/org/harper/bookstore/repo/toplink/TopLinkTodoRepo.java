package org.harper.bookstore.repo.toplink;

import java.util.List;

import oracle.toplink.expressions.Expression;
import oracle.toplink.expressions.ExpressionBuilder;
import oracle.toplink.queryframework.ReadAllQuery;

import org.harper.bookstore.domain.todo.TodoItem;
import org.harper.bookstore.domain.todo.TodoItem.Privilege;
import org.harper.bookstore.repo.TodoRepo;
import org.harper.bookstore.service.TransactionContext;

public class TopLinkTodoRepo extends TopLinkRepo implements TodoRepo {

	@Override
	public TodoItem getTodoItem(String key) {
		return (TodoItem) TransactionContext.getSession().readObject(
				TodoItem.class, new ExpressionBuilder().get("key").equal(key));
	}

	@Override
	public List<TodoItem> findValidTodoItems(Privilege filter) {
		ExpressionBuilder builder = new ExpressionBuilder();
		Expression exp = builder;
		if (null != filter)
			exp = exp.and(builder.get("privilege").lessThan(filter.ordinal()));

		ReadAllQuery raq = new ReadAllQuery(TodoItem.class, exp);
		raq.addOrdering(builder.get("privilege").descending());
		raq.addOrdering(builder.get("createDate").ascending());
		return (List<TodoItem>) TransactionContext.getSession().executeQuery(
				raq);
	}

}
