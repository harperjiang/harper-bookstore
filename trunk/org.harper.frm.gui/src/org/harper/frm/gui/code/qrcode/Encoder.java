package org.harper.frm.gui.code.qrcode;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.harper.frm.gui.code.qrcode.encoder.AlphanumericEncoder;

public abstract class Encoder {

	private static Map<EncodeMode, Encoder> encoders;

	static {
		encoders = new HashMap<EncodeMode, Encoder>();
		encoders.put(EncodeMode.AlphanumericMode, new AlphanumericEncoder());
	}

	public static InputStream encode(String input, EncodeMode mode) {
		Encoder encoder = encoders.get(mode);
		if (null == encoder)
			throw new NotImplementedException("No Encoders for Mode:"
					+ mode.name());
		return encoder.encode(input);
	}

	public abstract InputStream encode(String input);
}
