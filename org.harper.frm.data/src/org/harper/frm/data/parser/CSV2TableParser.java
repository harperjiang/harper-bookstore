package org.harper.frm.data.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.harper.frm.data.MemoryTable;


public class CSV2TableParser implements IParser {

	private boolean headerExists = true;

	private String charSet;

	public boolean isHeaderExists() {
		return headerExists;
	}

	public void setHeaderExists(boolean headerExists) {
		this.headerExists = headerExists;
	}

	public CSV2TableParser() {
		this(true);
	}

	public CSV2TableParser(boolean hasHeader) {
		this(hasHeader, null);
	}

	public CSV2TableParser(boolean hasHeader, String charSet) {
		super();
		setHeaderExists(hasHeader);
		this.charSet = charSet;
	}

	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	protected boolean testLine(String line) {
		boolean completed = true;
		boolean wrapper = false;
		for (int i = 0; i < line.length(); i++) {
			char current = line.charAt(i);
			if (completed) {
				switch (current) {
				case '\"':
					wrapper = true;
					completed = false;
					break;
				case ',':
					// Blank Column
					completed = true;
					break;
				default:
					break;
				}
			} else {
				switch (current) {
				case '\"':
					if (wrapper
							&& ((i + 1 < line.length() && line.charAt(i + 1) == ',') || (i + 1 == line
									.length()))) {
						// End of a group
						wrapper = false;
						i++;
						completed = true;
					}
					break;
				case ',':
					completed = !wrapper;
				default:
				}
			}
		}
		return completed;
	}

	protected String readLine(BufferedReader br) throws IOException,
			ParseException {
		StringBuffer buffer = new StringBuffer();
		String line = br.readLine();
		if (line == null)
			return null;
		buffer.append(line);
		while (!testLine(buffer.toString())) {
			String next = br.readLine();
			// If no more line, then there were format exception
			if (next == null)
				throw new ParseException("Cannot fetch a complete Line");
			buffer.append("\n").append(next);
		}
		return buffer.toString();
	}

	public Object parse(InputStream input) throws ParseException,
			UnsupportedOperationException, IOException {
		MemoryTable table = null;
		InputStreamReader is = StringUtils.isEmpty(charSet) ? new InputStreamReader(
				input)
				: new InputStreamReader(input, charSet);
		BufferedReader br = new BufferedReader(is);
		String line = null;

		line = readLine(br);
		String[] titles = parseLine(line);
		if (headerExists) {
			table = new MemoryTable(titles);
		} else {
			table = new MemoryTable(titles.length);
			table.addRow(titles);
		}
		while ((line = readLine(br)) != null) {
			String[] lineContent = null;
			if ((lineContent = parseLine(line)) == null) {
				table.addRow(new String[table.getColumnCount()]);
			} else
				table.addRow(lineContent);
		}
		br.close();
		return table;
	}

	protected String[] parseLine(String input) throws ParseException {
		boolean strMode = false;
		if (StringUtils.isEmpty(input))
			return null;
		ArrayList<String> parts = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			char chr = input.charAt(i);
			if (strMode) {
				if (chr != '"')
					builder.append(chr);
				else {
					if (input.length() > i + 2) {
						char next = input.charAt(i + 1);
						switch (next) {
						case '"':// Only add a '"'
							builder.append("\"");
							i++;
							break;
						case ',':// start a new element;
							parts.add(builder.toString());
							builder.delete(0, builder.length());
							i++;
							strMode = !strMode;
							break;
						default:
							throw new ParseException("Error parse ^" + input
									+ "$ at " + i
									+ ", expecting \" or , while encountering"
									+ (int) next + " " + next);
						}
					}
				}
			} else {
				switch (chr) {
				case ',':
					parts.add(builder.toString());
					builder.delete(0, builder.length());
					break;
				case '"':
					if (input.length() > i + 2)
						if (input.charAt(i + 1) != '"')
							strMode = !strMode;
						else {
							builder.append("\"");
							i++;
						}
					break;
				default:
					builder.append(chr);
					break;
				}
			}
		}
		parts.add(builder.toString());
		return parts.toArray(new String[parts.size()]);
	}
}
