package org.harper.frm.gui.code;

import java.util.List;

import org.apache.commons.lang.Validate;

public class BitwiseUtils {

	public static byte[] convert(List<Boolean> input) {
		Validate.isTrue(input.size() % 8 == 0);
		byte[] result = new byte[input.size() / 8];
		for (int index = 0; index < input.size(); index += 8) {
			byte current = 0;
			for (int i = 0; i < 8; i++) {
				boolean bit = input.get(index + i);
				if (bit)
					current |= 1 << (7 - i);
			}
			result[index / 8] = current;
		}
		return result;
	}

}
