package org.harper.frm.gui.code.qrcode;

import java.io.IOException;
import java.io.InputStream;

import org.harper.frm.gui.code.qrcode.draw.Block;

public interface CodeModel {

	int getSize();

	InputStream getInputStream() throws IOException;
	
	int getFormat();
	
	int getMaskType();
	
	int getVersion();
	
	Iterable<Block> getBlocks();
}
