package cn.imhtb.antlive.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.live.v20180801.LiveClient;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.vod.v20180717.VodClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author PinTeh
 * @date 2020/4/14
 */
@Configuration
public class TencentConfig {

    @Value("${tencent.live.iii}")
    private String iii;

    @Value("${tencent.live.kkk}")
    private String kkk;


    @Bean
    public LiveClient getLiveClient(){
        Credential cred = new Credential(iii, kkk);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("live.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        return new LiveClient(cred, "ap-chengdu", clientProfile);
    }

    @Bean
    public COSClient getCosClient(){
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(iii, kkk);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-chengdu");
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    @Bean
    public OcrClient getOcrClient(){
        Credential cred = new Credential(iii, kkk);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("ocr.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        return new OcrClient(cred, "ap-guangzhou", clientProfile);
    }

    @Bean
    public VodClient getVodClient(){
        Credential cred = new Credential(iii, kkk);
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("vod.tencentcloudapi.com");
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        //Region公共参数，本接口不需要传递此参数。
        return new VodClient(cred, "ap-chongqing", clientProfile);
    }
}
