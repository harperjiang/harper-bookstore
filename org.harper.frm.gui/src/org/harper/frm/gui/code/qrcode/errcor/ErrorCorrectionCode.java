package org.harper.frm.gui.code.qrcode.errcor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.harper.frm.gui.code.GF256Utils;

public class ErrorCorrectionCode {

	static Map<Integer, int[]> polynomials;
	static {
		polynomials = new HashMap<Integer, int[]>();
		polynomials.put(13, new int[] { 1, 137, 73, 227, 17, 177, 17, 52, 13,
				46, 43, 83, 132, 120 });
		polynomials.put(10, new int[] { 1, 216, 194, 159, 111, 199, 94, 95,
				113, 157, 193 });
		polynomials.put(7, new int[] { 1, 127, 122, 154, 164, 11, 68, 117});
	}

	public static List<Integer> generate(List<Integer> input, int total) {
		int[] polynomial = polynomials.get(total - input.size());
		if (null == polynomial)
			throw new IllegalArgumentException();

		List<Integer> result = new ArrayList<Integer>();
		result.addAll(input);
		int remainder = total - 1;
		while (remainder >= total - input.size()) {
			int factor = 0;
			while ((factor = result.get(0)) == 0) {
				result.remove(0);
			}
			for (int i = 0; i < polynomial.length; i++) {
				if (i >= result.size()) {
					result.add(GF256Utils.mul(factor, polynomial[i]));
				} else {
					result.set(
							i,
							GF256Utils.add(result.get(i),
									GF256Utils.mul(factor, polynomial[i])));
				}
			}
			remainder--;
		}
		while (result.get(0) == 0)
			result.remove(0);
		return result;
	}
}
