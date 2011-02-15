package org.harper.bookstore.service;

import java.util.Stack;

import oracle.toplink.sessions.Session;

public class TransactionContext {

	private static ThreadLocal<Stack<Session>> context = new ThreadLocal<Stack<Session>>() {
		@Override
		protected Stack<Session> initialValue() {
			return new Stack<Session>();
		}
	};
	
	public static Session getSession() {
		return (Session)get().peek();
	}
	
	public synchronized static Stack<Session> get(){
		return context.get();
	}
	
	public static String CONTEXT= "Context";
	
	public static String CONTEXT_TYPE = "Context.type";
}
