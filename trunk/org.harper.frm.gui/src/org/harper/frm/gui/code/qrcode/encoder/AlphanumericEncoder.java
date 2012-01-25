package org.harper.frm.gui.code.qrcode.encoder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.harper.frm.gui.code.BitwiseUtils;
import org.harper.frm.gui.code.qrcode.Encoder;

public class AlphanumericEncoder extends Encoder {

	static Map<Character, Integer> tables;
	static {
		tables = new HashMap<Character, Integer>();
		tables.put('0', 0);
		tables.put('6', 6);
		tables.put('C', 12);
		tables.put('I', 18);
		tables.put('O', 24);
		tables.put('U', 30);
		tables.put(' ', 36);
		tables.put('.', 42);
		tables.put('1', 1);
		tables.put('7', 7);
		tables.put('D', 13);
		tables.put('J', 19);
		tables.put('P', 25);
		tables.put('V', 31);
		tables.put('$', 37);
		tables.put('/', 43);
		tables.put('2', 2);
		tables.put('8', 8);
		tables.put('E', 14);
		tables.put('K', 20);
		tables.put('Q', 26);
		tables.put('W', 32);
		tables.put('%', 38);
		tables.put(':', 44);
		tables.put('3', 3);
		tables.put('9', 9);
		tables.put('F', 15);
		tables.put('L', 21);
		tables.put('R', 27);
		tables.put('X', 33);
		tables.put('*', 39);
		tables.put('4', 4);
		tables.put('A', 10);
		tables.put('G', 16);
		tables.put('M', 22);
		tables.put('S', 28);
		tables.put('Y', 34);
		tables.put('+', 40);
		tables.put('5', 5);
		tables.put('B', 11);
		tables.put('H', 17);
		tables.put('N', 23);
		tables.put('T', 29);
		tables.put('Z', 35);
		tables.put('-', 41);
	}

	@Override
	public InputStream encode(String input) {
		List<Boolean> data = new ArrayList<Boolean>();
		// Header
		for (boolean bit : prefix)
			data.add(bit);

		// Word Length
		data.addAll(convert(input.length(), 9));
		// Encoding Data
		for (int i = 0; i < input.length(); i += 2) {
			if (i + 1 >= input.length()) {
				data.addAll(convert(tables.get((char) input.charAt(i)), 6));
			} else {
				int first = tables.get((char) input.charAt(i));
				int second = tables.get((char) input.charAt(i + 1));
				data.addAll(convert(first * 45 + second, 11));
			}
		}
		// Append Terminator
		for (int i = 0; i < 4; i++)
			data.add(false);

		// Append Zero
		while (data.size() % 8 != 0)
			data.add(false);

		byte[] byteArray = BitwiseUtils.convert(data);

		return new ByteArrayInputStream(byteArray);
	}

	protected List<Boolean> convert(int data, int length) {
		int max = 0;
		while (1 << max < data)
			max++;
		List<Boolean> buffer = new ArrayList<Boolean>();
		for (int i = max; i >= 0; i--)
			buffer.add((data & (1 << i)) != 0);
		if (!buffer.get(0))
			buffer.remove(0);
		while (buffer.size() < length)
			buffer.add(0, false);
		return buffer;
	}

	static final boolean[] prefix = new boolean[] { false, false, true, false };
}
