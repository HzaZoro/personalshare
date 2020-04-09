package cn.pirateswang.personalshare;

import cn.pirateswang.PersonalshareApplication;
import cn.pirateswang.common.tools.QcloudClientTools;
import cn.pirateswang.core.auth.controller.AuthLoginController;
import com.alibaba.fastjson.JSON;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.crypto.Cipher;
import java.io.File;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

/**
 * @author wangqiang
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PersonalshareApplication.class)
public class BaseJunitTest {
    
    Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private AuthLoginController controller;
    
    
    @Test
    public void testCos(){
        COSClient cosClient = QcloudClientTools.getInstance().getCosClient();
        
        //查询存储桶的列表
        List<Bucket> buckets = cosClient.listBuckets();
        if(buckets != null && !buckets.isEmpty()){
            log.info("【测试对象云存储】查询到{}个存储桶",buckets.size());
            for (int cnt = 0;cnt < buckets.size();cnt ++){
                Bucket bucket = buckets.get(cnt);
                String name = bucket.getName();
                String location = bucket.getLocation();
                log.info("【测试对象云存储】name:{} ===== location:{}",name,location);
            }
            
        }else{
            log.info("【测试对象云存储】未查询到存储桶列表");
        }

        Bucket bucket = buckets.get(0);
        //拿到该存储桶名称
        String name = bucket.getName();
        String location = bucket.getLocation();
        
        String filePath = "C:\\Users\\EDZ\\Desktop\\项目\\ipad测试用文件\\蜂蜜.jpg";
        String filaName = "蜂蜜.jpg";
        File file = new File(filePath);
        String key = "test/"+filaName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(name, key, file);

        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        log.info("【测试对象云存储】存储的数据返回值为:",JSON.toJSONString(putObjectResult));

    }

    public static void main(String[] args) {
    }
    
    public static void test1(){
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
