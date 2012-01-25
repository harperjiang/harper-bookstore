package org.harper.frm.gui.code.qrcode.errcor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ErrorCorrectionCodeTest {

	@Test
	public void testGenerate() {
		List<Integer> input = new ArrayList<Integer>();
		input.add(16);
		input.add(32);
		input.add(12);
		input.add(86);
		input.add(97);
		input.add(128);
		input.add(236);
		input.add(17);
		input.add(236);
		input.add(17);
		input.add(236);
		input.add(17);
		input.add(236);
		List<Integer> output = ErrorCorrectionCode.generate(input, 26);
		System.out.println(output);
	}

}
