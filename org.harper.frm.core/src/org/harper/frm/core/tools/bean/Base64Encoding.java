/*
 * Created on 2005-11-15
 * 
 */
package org.harper.frm.core.tools.bean;


/**
 * @author Harper Jiang
 * 
 * @version Revision 0.0.1
 */
public class Base64Encoding {

    private static String content = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    
    protected static void enBase(byte[] buf,int[] out) {
        out[0] = ((buf[0] & 0xfc) >> 2) & 0x3f;
        out[1] = ((buf[0] & 0x3) << 4) | (((buf[1] & 0xf0) >> 4) & 0xf);
        out[2] = ((buf[1] & 0xf) << 2) | (((buf[2] & 0xc0) >> 6) & 0x3);
        out[3] = (buf[2] & 0x3f);
    }
    
    protected static void deBase(int[] buf,byte[] out) {
        out[0] = (byte)((buf[0] << 2)|(((buf[1] & 0x30)>>4) & 0x3 ));
        out[1] = (byte)(((buf[1] & 0xf) << 4)|((buf[2] & 0x3c)>>2)&0xf);
        out[2] = (byte)(((buf[2] & 0x3) << 6)| buf[3]);
    }
    
    public static byte[] encode(byte[] input) {
        int len = input.length/3;
        int complement = (input.length%3==0)?0:(len+1)*3 - input.length;
        byte[] output = new byte[(len + (input.length%3 ==0?0:1))*4];
        byte[] buf = new byte[3];
        int[] index = new int[4];
        for(int i = 0 ; i < len ; i++) {
            buf[0] = input[i*3];buf[1] = input[i*3+1];buf[2]=input[i*3+2];
            enBase(buf,index);
            for(int j=0;j<4;j++)output[i*4+j]=(byte)content.charAt(index[j]);
        }
//        int remain;
        if(complement != 0) { // more
            int i;
            for(i = 0;i< (3 - complement);i++)buf[i] = input[len*3+i];
            for(;i< 3;i++)buf[i] = 0;
            enBase(buf,index);
            for(int j=0;j<4;j++)output[len*4+j]=(byte)content.charAt(index[j]);
            for(i = 0 ; i < complement ; i++)
                output[output.length-i-1]='=';
        }
        return output;
    }
    
    public static byte[] decode(byte[] input) {
        int i;
        for(i = 0 ; input[input.length-1-i] =='=';i++);
        int bound = i;
        byte[] output = new byte[input.length*3/4 - bound];
        int len = input.length/4;
        int[] buffer = new int[4];
        byte[] dd = new byte[3];
        for(i = 0 ; i < len - 1; i++) {
            for(int j = 0 ; j < 4;j++)
                buffer[j] = content.indexOf(input[i*4+j]);
            deBase(buffer,dd);
            for(int j = 0 ; j < 3 ; j++)output[i*3+j]=dd[j];       
        }
        for(int j = 0 ; j < 4;j++)
            buffer[j] = content.indexOf(input[(len-1)*4+j]);
        deBase(buffer,dd);
        for(int j = 0 ; j < 3-bound ; j++)output[(len-1)*3+j]=dd[j];
        return output;
    }
   /* 
    public static void main(String[] args)throws Exception {
        
    	
//        byte[] res = decode("eHV0bzI6YWxKTk5FeVk=".getBytes());
//        byte[] a00 = "xuto2:alJNNEyY".getBytes();
//        byte[] res = encode(a00);
//        for(int i = 0 ; i < res.length ;i++)
//            System.out.print((char)res[i]);
    	Object sb = new SerializableBean();
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	ObjectOutputStream oos = new ObjectOutputStream(baos);
    	oos.writeObject(sb);
    	oos.close();
    	byte[] bytes = baos.toByteArray();
    	baos.close();
    	System.out.println(bytes.length);
    	
    	byte[] encoded = encode(bytes);
    	System.out.println(encoded.length);
    	
    	byte[] decoded = decode(encoded);
    	System.out.println(decoded.length);
    	
    	ByteArrayInputStream bais = new ByteArrayInputStream(decoded);
    	ObjectInputStream ois = new ObjectInputStream(bais);
    	Object o = ois.readObject();
    	ois.close();
    	bais.close();
    	System.out.println(o.getClass());
    }*/
}
