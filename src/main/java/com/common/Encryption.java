package com.common;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * AES加密CBC模式
 * @author  http://my.oschina.net/Jacker/blog/86383
 * AES加密CBC模式兼容互通四种编程语言平台【PHP、Javascript、Java、C#】
 */
public class Encryption
{
    public static void main(String args[]) throws Exception {
    	String str="One 中文 空格 ‘’ & /";
    	System.out.println("需要加密的字符串:"+str);
    	String miwen=encrypt(str);
        System.out.println("密文为"+miwen);
        System.out.println("解密后的数据:" +desEncrypt(miwen));
    }

    /**
     * 
     * @param data
     * @return  加密密文
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        try {
            String key = "paperpass!@#$%^&";
            String iv = "paperpass!@#$%^&";

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return Base64.encode(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	/**
	 *  解密密文
	 * @param data
	 * @return
	 * @throws Exception
	 */
    public static String desEncrypt(String data) throws Exception {
        try
        {
            String key = "paperpass!@#$%^&";
            String iv = "paperpass!@#$%^&";
            byte[] encrypted1 = Base64.decode(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
 
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString.trim();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}