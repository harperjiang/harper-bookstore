package org.harper.bookstore.repo.toplink;

import java.util.Collection;

import oracle.toplink.expressions.ExpressionBuilder;
import oracle.toplink.sessions.UnitOfWork;

import org.apache.commons.lang.StringUtils;
import org.harper.bookstore.repo.CommonRepo;
import org.harper.bookstore.service.TransactionContext;
import org.harper.frm.toplink.SessionManager;

public class TopLinkCommonRepo extends TopLinkRepo implements CommonRepo {

	@Override
	public Object store(Object object) {
		UnitOfWork uow = (UnitOfWork) TransactionContext.getSession();
		return uow.deepMergeClone(object);
	}

	@Override
	public String getNumber(String type) {
		UnitOfWork uow = (UnitOfWork) TransactionContext.getSession();
		NumGen gen = (NumGen) uow.readObject(NumGen.class,
				new ExpressionBuilder().get("type").equal(type));
		if (null == gen)
			throw new IllegalArgumentException("No Such Number:" + type);
		String number = gen.nextNumber();
		return number;
	}

	public static class NumGen {

		private String type;

		private String prefix;

		private String suffix;

		private int current;

		private int length;

		public String nextNumber() {
			StringBuilder builder = new StringBuilder();
			if (!StringUtils.isEmpty(prefix))
				builder.append(prefix);
			builder.append(current++);
			if (!StringUtils.isEmpty(suffix))
				builder.append(suffix);

			while (length > builder.length()) {
				builder.insert(null == prefix ? 0 : prefix.length(), "0");
			}
			return builder.toString();
		}

		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		public String getSuffix() {
			return suffix;
		}

		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}

		public int getCurrent() {
			return current;
		}

		public void setCurrent(int current) {
			this.current = current;
		}

		public int getLength() {
			return length;
		}

		public void setLength(int length) {
			this.length = length;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

	}

	@Override
	public void store(Collection<?> cols) {
		UnitOfWork uow = (UnitOfWork) TransactionContext.getSession();
		for (Object element : cols)
			uow.deepMergeClone(element);
	}
}
