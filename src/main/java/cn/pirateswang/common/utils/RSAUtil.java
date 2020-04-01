package cn.pirateswang.common.utils;

import cn.pirateswang.common.config.ServiceConfig;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {
	// 日志输出
	private static Logger log = LoggerFactory.getLogger(RSAUtil.class);
	static ServiceConfig serviceConfig = SpringUtil.getBean(ServiceConfig.class);

    private static final String ALGORITHM = "RSA";
    private static final String PUBLICK_EY = "PUBLICK_EY";
    private static final String PRIVATE_KEY = "PRIVATE_KEY";
    /**
     * 加密算法
     */
    private static final String CIPHER_DE = "RSA";
    /**
     * 解密算法
     */
    private static final String CIPHER_EN = "RSA";
    /**
     * 密钥长度
     */
    private static final Integer KEY_LENGTH = 1024;

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 256;

    /**
     * 生成秘钥对，公钥和私钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, Object> genKeyPair() throws NoSuchAlgorithmException {
        Map<String, Object> keyMap = new HashMap<String, Object>();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_LENGTH); // 秘钥字节数
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        keyMap.put(PUBLICK_EY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 公钥加密
     *
     * @return
     * @throws InvalidKeySpecException
     */
    public static String encryptByPublicKey(String content) throws Exception {
    	
    	byte[] data = content.getBytes();
    	String userLoginRsaPublicKey = serviceConfig.getUserLoginRsaPublicKey();
     	InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(userLoginRsaPublicKey);
// 	    InputStream resourceStream = new FileInputStream(userLoginRsaPublicKey);
    	BufferedReader br = new BufferedReader(new InputStreamReader(resourceStream));
    	String read = "";
    	StringBuilder ab = new StringBuilder();
    	while((read = br.readLine()) != null) {
    		if(read.charAt(0) == '-') {
    			continue;
    		}
    		ab.append(read + "\r");
    	}
        // 得到公钥
        byte[] keyBytes = Base64.decodeBase64(ab.toString().getBytes());
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        Key key = keyFactory.generatePublic(x509EncodedKeySpec);
        // 加密数据，分段加密  
        Cipher cipher = Cipher.getInstance(CIPHER_EN);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        int inputLength = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        while (inputLength - offset > 0) {
            if (inputLength - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offset, inputLength - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        byte[] btt = Base64.encodeBase64(encryptedData);
        out.close();
        return new String(btt);
    }

    /**
     * 私钥解密
     *
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String content) throws Exception {
    	log.info("私钥解密数据 == 传入参数:{}",content);
    	byte[] data = Base64.decodeBase64(content);
    	String userLoginRsaPrivateKey = serviceConfig.getUserLoginRsaPrivateKey();
   	    InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(userLoginRsaPrivateKey);
//   	    InputStream resourceStream = new FileInputStream(userLoginRsaPrivateKey);
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(resourceStream));
    	String read = "";
    	StringBuilder ab = new StringBuilder();
    	while((read = br.readLine()) != null) {
    		if(read.charAt(0) == '-') {
    			continue;
    		}
    		ab.append(read + "\r");
    	}
    	
        // 得到私钥  
        byte[] keyBytes = Base64.decodeBase64(ab.toString().getBytes());
        PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        Key key = keyFactory.generatePrivate(pKCS8EncodedKeySpec);
        // 解密数据，分段解密
        Cipher cipher = Cipher.getInstance(CIPHER_DE);
        cipher.init(Cipher.DECRYPT_MODE, key);
        int inputLength = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        byte[] tmp;
        while (inputLength - offset > 0) {
            if (inputLength - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offset, inputLength - offset);
            }
//            out.write(cache, 0, cache.length);
            out.write(cache);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData);
    }

    /**
     * 获取公钥
     *
     * @param keyMap
     * @return
     */
    public static String getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PUBLICK_EY);
        String str = new String(Base64.encodeBase64(key.getEncoded()));
        return str;
    }

    /**
     * 获取私钥
     *
     * @param keyMap
     * @return
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        String str = new String(Base64.encodeBase64(key.getEncoded()));
        return str;
    }


//	
//    public static void main(String[] args) throws Exception {
//        Map<String, Object> keyMap = RSATest.genKeyPair();
//        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiurDb5kKc3VxzcQb8hgnH4eete44Lec/HTNqJCtK8xYlpXh594zKVVZS251WYrYfF0DfEqaWBAo9WjvxsfB7Qo4DYgTeJ+QW+MgiwKx9AfjpfAZrZypI9HwQ+E1bI/zwt1uyqYAIMKu/z4RJHj1GTAji8xhci5m6MPm/nEkaF0tM9bG6KqTjJtO9wTRsz3WCuLJoHbDIvwvD0xJvwWfCB5474oCYRYF6t8RkZeTG4BOcXS1PCdhwNU1MYMxpFVsKEkOV9rP9KVveJX4KZHYlgE76yrJhI3DadJpEm7N0Uiee/sgq4nb68SlwybRY+ZHunYIqhyTnwJfcKw/8vmcLuQIDAQAB";
//        String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCK6sNvmQpzdXHNxBvyGCcfh5617jgt5z8dM2okK0rzFiWleHn3jMpVVlLbnVZith8XQN8SppYECj1aO/Gx8HtCjgNiBN4n5Bb4yCLArH0B+Ol8BmtnKkj0fBD4TVsj/PC3W7KpgAgwq7/PhEkePUZMCOLzGFyLmbow+b+cSRoXS0z1sboqpOMm073BNGzPdYK4smgdsMi/C8PTEm/BZ8IHnjvigJhFgXq3xGRl5MbgE5xdLU8J2HA1TUxgzGkVWwoSQ5X2s/0pW94lfgpkdiWATvrKsmEjcNp0mkSbs3RSJ57+yCridvrxKXDJtFj5ke6dgiqHJOfAl9wrD/y+Zwu5AgMBAAECggEAGekwCuDA0khVscuiasWWhosFT2nfkLBRVWEBqz1n/W9kFjkihsn7827mhZATTNYrdmrjMNdho99FEk+cBVSVqRRUDTRbO5WPs1ehI88IghWrdza7UcLRWMjLYYUMOuoy/ABekr8OyAVBAbPq73j+Y7BEpJMmr+M8l2X+F2iLNUzP4w38XNvGvrI0YvpdINwfDiaBN07A0rpF68vDlV6hfY7AUPCjY3bMejLXNah67toDzgBdxb6ZUfiz7OM3Crlsx6r7jufI64MDRsJpAPlkY1tj6wy1ldGsvEb6se3i+yAAVf17IeUEfNWCmanP6SgDLUrXYKPz1WgMqLXYFqpHAQKBgQDFE1E2uI97DBGq4m+Gi3FVGJ3Q8WjaCLm7UZbcTmtYODRD3YtsYXU1aP/EVO5pqGL0m0k99WVfW3Vttod5u1vKOGDE5cxIthWXaSuXvC5o/1Ush2eDDE6OONG16qkQeRD/SpYxE83DWzaRRxWEezIRmE8b/b5nHe0nTn9hM9d2KwKBgQC0c9h72MhDXJTGB1b90jsiBHzQLqCO2UV0RiN2m9G29UXTKs002h4H96l0U/wMT2CEEjjuGo6P1XHh5hlK3wNywppfRG/q/8HyF1gmMEWdExMei/3JQzZZWC/zXRda8n3sT7emDPuDRlNjNqH69ywAwzsAORRQhe+wfVmjUjfXqwKBgQCgqBjC1T4Sf0o/1ow4UKC5UHIHDWX+qLJQpDiprDwSFO92i7rHRcO5ILmwodQhyiWxZo4UtmJHlFVsdSqH8FbpyYAJw5P/ioCbiB8DeWMUMWKMBG48BOJdSrCa7ouYZDmZBILfy1codMlNMXe3In80nMeQ+BAqG9li+7kX0p1JNQKBgQCE+Cg5elB3cq0eDyKG/nccZfoi3XjqSifnuhc/uyoZizrck9mKbH9cl56CXbCI0aTpLRROP7t1OW8laoyQoyzDT6q5MJDXtYaQgasu2LszN28LP1TPDXvq8hTDW4FuPumTTFqjPUXIQfYIUaWibKg8F6BOVcEmZMtuXa/DrT4A6QKBgQCLQDrbfjEuYFH5dG1qX19nmXKCd0jEr8IEXb80gDxj54QC1VTs3k3jR/uYbraYzF7r+leRe3g2jPGwX3qI/Q0+NjPGOKlE6dIm9h03qAPCj0cwF5Azbb6AFijHxDtbLK5fgeB+IK+X/VPIyg/C/nQgl6iWPQoV8GmZmCXemVQ38Q==";
//        System.out.println("公钥：" + publicKey);
//        System.out.println("私钥：" + privateKey);
//        // 公钥加密
//        String sourceStr = "123456";
//        System.out.println("加密前：" + sourceStr);
//        byte[] encryptStrByte = RSATest.encryptByPublicKey(sourceStr.getBytes(), publicKey);
//        byte[] btt = Base64.encodeBase64(encryptStrByte);
//        String encryptStr = new String(btt);
//        System.out.println("加密后：" + new String(btt));
//        System.out.println("长度：" + encryptStr.length());
//        // 私钥解密
//        byte[] decryptStrByte = RSATest.decryptByPrivateKey(Base64.decodeBase64(encryptStr), privateKey);
//        String sourceStr_1 = new String(decryptStrByte);
//        System.out.println("解密后：" + sourceStr_1);
//    }
	 
}  
