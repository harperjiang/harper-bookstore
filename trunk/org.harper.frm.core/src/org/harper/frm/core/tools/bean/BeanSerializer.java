package org.harper.frm.core.tools.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * @author Harper Jiang
 * 
 */
public class BeanSerializer {
	public static String serialize(Object obj) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.close();
			String ret = new String(Base64Encoding.encode(baos.toByteArray()));
			baos.close();
			return ret;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Object deserialize(String str) {
		if(str == null || str.length() == 0)
			return null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(Base64Encoding
					.decode(str.getBytes()));
			ObjectInputStream ois = new ObjectInputStream(bais);
			Object ret = ois.readObject();
			ois.close();
			bais.close();
			return ret;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
