package org.harper.frm.data.parser;


import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import java_cup.parser;
import java_cup.runtime.Scanner;


/**
 * Parse Text File based on an java cup parser.
 * 
 * @author Harper Jiang
 * @version 1.0 2009-03-15
 * @since Common 1.0
 * 
 * @see java_cup.parser
 */
public class CupTextParser implements IParser {

	private final Class<? extends Scanner> lexerClass;

	private final parser parser;

	public CupTextParser(Class<? extends Scanner> lexerClass, parser parser) {
		super();
		this.lexerClass = lexerClass;
		this.parser = parser;
	}

	public Object parse(InputStream input) throws ParseException,
			UnsupportedOperationException {
		try {
			Scanner scanner = lexerClass.getConstructor(InputStream.class)
					.newInstance(input);
			parser.setScanner(scanner);
			return parser.parse();
		} catch (InstantiationException e) {
			throw new UnsupportedOperationException(
					"Cannot Construct the Scanner", e);
		} catch (IllegalAccessException e) {
			throw new UnsupportedOperationException(
					"Cannot Construct the Scanner", e);
		} catch (IllegalArgumentException e) {
			throw new UnsupportedOperationException(
					"Cannot Construct the Scanner", e);
		} catch (InvocationTargetException e) {
			throw new UnsupportedOperationException(
					"Cannot Construct the Scanner", e);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}
}
