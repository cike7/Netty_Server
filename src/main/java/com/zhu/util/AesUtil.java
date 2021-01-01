package com.zhu.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;


public class AesUtil {
	//加密算法
    static final String algorithmStr = "AES/ECB/PKCS5Padding";
 
    static private KeyGenerator keyGen;
 
    static private Cipher cipher;
 
    static boolean isInited = false;
    
    //注意: 这里的password(秘钥必须是16位的)
    static final String keyBytes = "Mq~!@#$%^&*?./;'"; 
       
    private static  void init() {
        try { 
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 初始化此密钥生成器，使其具有确定的密钥长度。
        keyGen.init(128); //128位的AES加密
        try {    
                // 生成一个实现指定转换的 Cipher 对象。
            cipher = Cipher.getInstance(algorithmStr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        //标识已经初始化过了的字段
        isInited = true;
    }
 
    /**
     * 字符串加密操作
     * @param content 内容
     * @return 加密内容
     */
    public static byte[] encrypt(byte[] content) {
        byte[] encryptedText = null;
        if (!isInited) { 
            init();
        }

        try {
            Key key = new SecretKeySpec(keyBytes.getBytes("utf-8"), "AES");
            // 用密钥初始化此 cipher。
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //按单部分操作加密或解密数据
            encryptedText = cipher.doFinal(content);
        
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return encryptedText;
    }
    
    /**
     * 字节解密操作
     * @param content 内容
     * @return 解密后内容
     */
    public static byte[] decrypt(byte[] content) {
        try {
            byte[] keyStr = getKey(keyBytes);
            SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
            Cipher cipher = Cipher.getInstance(algorithmStr);//algorithmStr           
            cipher.init(Cipher.DECRYPT_MODE, key);//   ?  
            byte[] result = cipher.doFinal(content);
            return result; //     
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
     
    private static byte[] getKey(String password) {
        byte[] rByte = null;
        if (password!=null) {
            rByte = password.getBytes();
        }else{
            rByte = new byte[24];
        }
        return rByte;
    }   
     
}
