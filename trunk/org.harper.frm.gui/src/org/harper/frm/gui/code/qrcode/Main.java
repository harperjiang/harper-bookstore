package org.harper.frm.gui.code.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.harper.frm.gui.code.qrcode.codemodel.ECLevel;
import org.harper.frm.gui.code.qrcode.codemodel.V1Model;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		V1Model model = new V1Model();
		model.setMode(EncodeMode.AlphanumericMode);
		model.setEcLevel(ECLevel.L);
		model.setContent("CHOULAOBA");
		model.setMaskType(0);
		
		CodeDrawer drawer = new CodeDrawer();
		drawer.setPixelPerLine(3);
		BufferedImage image = drawer.draw(model);
		
		ImageIO.write(image, "png", new File("output.png"));
	}

}
