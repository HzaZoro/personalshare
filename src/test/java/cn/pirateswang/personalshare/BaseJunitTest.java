package cn.pirateswang.personalshare;

import cn.pirateswang.PersonalshareApplication;
import cn.pirateswang.core.auth.controller.AuthLoginController;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author liujy
 * @date 2019/11/6 14:56
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PersonalshareApplication.class)
public class BaseJunitTest {
    
    @Autowired
    private AuthLoginController controller;

    public static void main(String[] args) {
        try {
    String RSA_KEY_ALGORITHM = "RSA";
          String  publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDSUmOXyQmYYSnZacp0btvAZCOvCNPtzixAp7eJmzmAG4mgy/VgrY/s1BDLh9qTNHIRWXepUtwMrf1kYul/A45qE/2oxIbeeq4238YDWQ7ModOVXR9ytEHsT0jpCFvoYfYXYZnnoWRrLIBylQeXzqxbLDxxBxGCs4AjoRKh5S7nNQIDAQAB";
            // 待加密数据
            String data = "H19931001521";
            // 公钥加密
            byte[] pubKey = Base64.decodeBase64(publicKeyStr);

            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pubKey);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] enSign =  cipher.doFinal(data.getBytes());

            String encrypt = Base64.encodeBase64String(enSign);

            System.out.println("加密前:" + data);
            System.out.println("加密后:" + encrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testBase(){

        controller.logOut();
        
        
    }


}
