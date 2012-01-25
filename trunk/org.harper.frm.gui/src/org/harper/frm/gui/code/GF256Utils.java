package org.harper.frm.gui.code;

public class GF256Utils {

	static final int PRIME_POLYNOMINAL = 285;

	public static final int PRIME = 0x2;

	public static int add(int a, int b) {
		return (int) (a ^ b);
	}

	public static int mul(int a, int b) {
		int aval = a;
		int res = 0;
		for (int i = 7; i >= 0; i--) {
			if ((b & (1 << i)) > 0) {
				res ^= (aval << i);
			}
		}
		while (res >= 256) {
			res ^= (PRIME_POLYNOMINAL << (maxbit(res) - 8));
		}
		return (int) res;
	}

	public static int pow(int a, int b) {
		int count = 1;
		int res = a;
		while (count < b) {
			res = mul(res, a);
			count++;
		}
		return res;
	}

	public static int maxbit(int val) {
		int i = 0;
		while ((1 << i) < val) {
			i++;
		}
		if ((1 << i) == val)
			return i;
		return i - 1;
	}
}
