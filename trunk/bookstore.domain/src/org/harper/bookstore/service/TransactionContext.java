package org.harper.bookstore.service;

import java.util.Stack;

import oracle.toplink.sessions.Session;

import org.harper.frm.mediator.MediatorTransaction;

public class TransactionContext {

	private static ThreadLocal<Stack<Session>> context = new ThreadLocal<Stack<Session>>() {
		@Override
		protected Stack<Session> initialValue() {
			return new Stack<Session>();
		}
	};

	private static ThreadLocal<Stack<MediatorTransaction>> mcontext = new ThreadLocal<Stack<MediatorTransaction>>() {
		@Override
		protected Stack<MediatorTransaction> initialValue() {
			return new Stack<MediatorTransaction>();
		}
	};

	public static Session getSession() {
		return get().peek();
	}
	
	public static MediatorTransaction getMediatorTransaction() {
		return getMT().peek();
	}

	public synchronized static Stack<Session> get() {
		return context.get();
	}

	public synchronized static Stack<MediatorTransaction> getMT() {
		return mcontext.get();
	}
	
	public static String CONTEXT = "Context";

	public static String CONTEXT_TYPE = "Context.type";
}
