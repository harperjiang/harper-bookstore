package org.harper.frm.gui.swing.print;

import java.awt.Font;

public interface Print {

	int ALIGN_LEFT = 1;

	int ALIGN_CENTER = 2;

	int ALIGN_RIGHT = 4;

	int ALIGN_TOP = 8;

	int ALIGN_BOTTOM = 16;

	int HORIZONTAL = 0;

	int VERTICAL = 1;

	Font DEFAULT_FONT = new Font("宋体", Font.PLAIN, 12);
}
