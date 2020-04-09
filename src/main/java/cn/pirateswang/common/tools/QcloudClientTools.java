package cn.pirateswang.common.tools;

import cn.pirateswang.common.config.CosConfig;
import cn.pirateswang.common.utils.SpringUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Component;

@Component
public class QcloudClientTools {
    
    protected static QcloudClientTools tools = null;
    
    protected COSClient cosClient = null;


    public static QcloudClientTools getInstance(){
        if (tools == null){
            tools = new QcloudClientTools();
        }
        return tools;
    }
    
    public COSClient getCosClient(){
        if (cosClient == null){
            this.init();
        }
        return cosClient;
    }
    
    public void init(){
        CosConfig cosConfig = SpringUtil.getBean(CosConfig.class);
        String region = cosConfig.getRegion();
        String secretId = cosConfig.getSecretId();
        String secretKey = cosConfig.getSecretKey();
        
        //1.初始化用户身份信息
        BasicCOSCredentials basicCOSCredentials = new BasicCOSCredentials(secretId, secretKey);
        
        //2.设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        //clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region regionBean = new Region(region);
        ClientConfig clientConfig = new ClientConfig(regionBean);
        
        //3.生成cos客户端
        cosClient = new COSClient(basicCOSCredentials, clientConfig);
    }
    
    
}
