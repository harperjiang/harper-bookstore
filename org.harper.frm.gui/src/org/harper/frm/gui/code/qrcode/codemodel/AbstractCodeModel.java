package org.harper.frm.gui.code.qrcode.codemodel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.harper.frm.gui.code.GF256Utils;
import org.harper.frm.gui.code.qrcode.CodeModel;
import org.harper.frm.gui.code.qrcode.EncodeMode;
import org.harper.frm.gui.code.qrcode.Encoder;
import org.harper.frm.gui.code.qrcode.errcor.ErrorCorrectionCode;

public abstract class AbstractCodeModel implements CodeModel {

	static final int PAD1 = 0xEC;
	static final int PAD2 = 0x11;

	private EncodeMode mode;

	private String content;

	private ECLevel ecLevel = ECLevel.Q;
	
	private int maskType = 3;

	private InputStream inputStream;

	public EncodeMode getMode() {
		return mode;
	}

	public void setMode(EncodeMode mode) {
		this.mode = mode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	static final int FORMAT_MASK = 0x5412;

	public int getFormat() {
		int ecCode = getEcLevel().getCode();
		int maskCode = getMaskType();

		int origin = (maskCode | ecCode<<3) << 10;
		int divident = 0x537;
		
		int remain = origin;
		
		while(remain > 1023) {
			int tomove = GF256Utils.maxbit(remain) - 10;
			remain ^= (divident << tomove);
		}
		return (origin|remain) ^ FORMAT_MASK;
	}

	public InputStream getInputStream() throws IOException {
		if (inputStream != null)
			return inputStream;
		Validate.notNull(getMode());
		Validate.notNull(getEcLevel());
		// Data Code Words
		InputStream codeData = Encoder.encode(getContent(), getMode());

		List<Integer> data = new ArrayList<Integer>();
		int read = -1;
		while ((read = codeData.read()) != -1) {
			data.add(read);
		}
		
		// Padding
		int dataCodewordCount = getDataCodewordCount(getEcLevel());
		while (data.size() < dataCodewordCount) {
			data.add(PAD1);
			data.add(PAD2);
		}
		while (data.size() > dataCodewordCount) {
			data.remove(dataCodewordCount);
		}

		// Error Correction
		List<Integer> eccodeword = ErrorCorrectionCode.generate(data,
				getCodewordCount());

		byte[] content = new byte[data.size() + eccodeword.size()];
		for (int i = 0; i < data.size(); i++)
			content[i] = data.get(i).byteValue();
		for (int i = 0; i < eccodeword.size(); i++)
			content[i + data.size()] = eccodeword.get(i).byteValue();
		inputStream = new ByteArrayInputStream(content);
		return inputStream;
	}

	public ECLevel getEcLevel() {
		return ecLevel;
	}

	public void setEcLevel(ECLevel ecLevel) {
		this.ecLevel = ecLevel;
	}
	
	public int getMaskType() {
		return maskType;
	}

	public void setMaskType(int maskType) {
		this.maskType = maskType;
	}

	protected abstract int getCodewordCount();

	protected abstract int getDataCodewordCount(ECLevel level);
}
