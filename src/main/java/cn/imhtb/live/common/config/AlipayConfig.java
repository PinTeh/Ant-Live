package cn.imhtb.live.common.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author PinTeh
 */
@Slf4j
@Configuration
public class AlipayConfig {

    private static String path = "Q:\\Learning\\Live\\BackEnd\\AntLive\\src\\main\\resources\\static\\";

    /**
     * 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
     */
    public static String APP_ID = "2016092700610818";

    /**
     * 商户私钥，您的PKCS8格式RSA2私钥
     */
    public static String MERCHANT_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCXYUVdlZHDptHwRznP0+9AkaxhMwJU3HA39oeyStcStht94OhZFmnId8At5pboxigbGBSlngoBRRBJIDaSDB34PhHCKBROz+2xS01zDGAqznzSBBn1Mphrg6YSFseSHTqUSbQ8uFgIR5D4w1sGpXCzJl+5m5DcrX3TXY+untYS51mW9dCwi7I7gkmAl3o2XUIfhDKKNn7c77hVNlaEDz3Nv3DB+YuNwRxFFf6eJ/5kSIrhhzr4i1/DUjFfFsWkhSknBpsNAbrmECgAxDyFQ4vQyZB/IbjwbArfdFXOJ+tYkx46A9vkzdmaFGoc+BWSlmAYnSMOOgKCo5GSWzp3X0ibAgMBAAECggEAIwrmZtKP6q28XZXvYRfbg6P1No77VC+vXpUYNoKypOc5F/uvbagNltV5zGZbusjcUeFSLkCWalLVgUgOueJKMcQUklB62v+xM2COLjrILECejnNamvM6a3EPSZKyYjWX8Ona6k92OXxvp9Z5ROGZZC9W632CnEMhJIO9Fhw4zCka2p4wIt2b0xeg+O0WbjZ5HB0K2ZGSd/yRUTuQC7hDqtzFyaRHyYX8xKMzTJhf0YkoJ++8NZKci9rIEKSbDz9DcT51fa0GDOnPbU2ubiP+1v0W+S3Vyb8bj+HfPUJP+QDf+MUBNRUwrAVRAhGpJS0qs+v8H4jI5xufD1Jnmp0MOQKBgQDzJQc9Dvp6pSdRED54PDpLTw2kjlJX0ji7AuhhEwBhEa5AIpKIZuOMV1c3C6RZxp6ta+QBOg1RO8Th433dlv0auaoxaPVuOWt1eZMs8RNPvhyjnPEDEUMk+QzDUNBaWbQvActgr3R5cyvuzyKQ9D2ksfuFM0wliY0u+YPsbMfglwKBgQCfYjRSgyWutULsajz5NtoYb1UCVrw+gSv5U16gOL5H6wVZnsbihS4DkxjWupigr4ipYkSKmb5C7ubL1teZeMMfJBx/Th2MtqKiCjxSDLsx4ZKNKwGQb0jv6Bn/v51wX0sMawviBaOGgPMYWL2NjDw3bO5CSVbZAmSphUryF89UnQKBgG3In08Tb0dnQKxacEprr0qHpDpTxJxAoDrYYkJKiRZLHGl68rzB8CZS3V9OU2PxyX22RFnFc3PlT81KoW8nGJszj8QFhyPAq2N8hFZ7d2O/DEFXwH7JEY8lIH0kBR92d2PuJH1AW4/mz8RhJFNc5GRztIR9F/mu9N9r7AQZTdp7AoGAR1bXgI8/EKIFYHh07j+FAybh7/gOrhN3MVQFqMV9NgeWD3UoJD/kRqlSltpScqnfC6H2d+s05BS2rUpumj0ZaRjqwHigdVlSFiFquncEWuFSjWlQe+uzw94ptPV5bNDacKaBXRuVBCE3rhAaV/8K/Ov/ASB+hQezMsV8tDvAC2kCgYEApeV0SFIAqlTLZ9/9cBMFVpFpicgSVniZ5pVUt2FaiB38oo28BzSR9DBTYOhkWAabLqNtHvdspmxLQFJWmNx2LVxE2+4bENED1WWhnoQldGSAtOXMwUzXz/CYbvF6hAtjBDrCdvZUrzmmP0H+TmX7VcD66B/7NhcrpuG55Ln0bGU=";

    /**
     * 支付宝公钥, <a href="https://openhome.alipay.com/platform/keyManage.htm">查看地址</a> 对应APPID下的支付宝公钥。
     */
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjMib+qYHVQ1m6FqHovVHiXIa/0hVxQFZDirQTN71iOkk60CxAzrj+8wxKGS/S2+sTsh7BBDM9bIngZTkb2lgw+YMEBBA2H342CxxOPpKK/VLSEDCVkla6iyjKja7R5SXlXw5EZX5XzclcDnKpQKg1P/uXfZLiEj4Oqj7Se+L6/q5hIJ5aAi0UxTewcFZtSz/UhUvzzeyxm4D7FBvAMdMdpT9f18IaIYH9KMi+uffKY3UGzqUtUMEhiQETjWzRzARIbW4Xam5H0mim1YjxfXLoMt41xCjcXlZl3b3BLM/TAnF1zgFpABT3PkjdofYFbfNPmOuUd9alhUZo45YPfZ86wIDAQAB";

    /**
     * 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public static String notify_url = "http://www.imhtb.cn:9000/bill/alipayNotifyNotice";

    /**
     * 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     */
    public static String return_url = "http://www.imhtb.cn/#/center/wallet";

    /**
     * 签名方式
     */
    public static String SIGN_TYPE = "RSA2";

    /**
     * 字符编码格式
     */
    public static String CHARSET = "utf-8";

    /**
     * 支付宝网关
     */
    public static String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";

    /**
     * 应用公钥证书路径
     */
    public static String app_cert_path = "crt/appCertPublicKey.crt";

    /**
     * 支付宝公钥证书文件路径
     */
    public static String alipay_cert_path = "crt/alipayCertPublicKey_RSA2.crt";

    /**
     * 支付宝CA根证书文件路径
     */
    public static String alipay_root_cert_path = "crt/alipayRootCert.crt";

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            // 支付宝网关
            String logPath = "C:\\";
            writer = new FileWriter(logPath + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Bean
    public DefaultAlipayClient getDefaultAlipayClient() {
        // Judge system environment
        String osName = System.getProperty("os.name");
        log.info("[----------System environment----------]" + osName);
        String path = null;
        if (osName.startsWith("Windows")) {
            // Windows
            try {
                path = ResourceUtils.getURL("classpath:").getPath();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            path += "static/";
            log.info("[----------Windows file path----------]" + path);
        } else {
            // unix or linux
            path = System.getProperty("user.dir");
            path += "/";
            log.info("[----------Unix or Linux file path----------]" + path);
        }

        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl(AlipayConfig.GATEWAY_URL);
        certAlipayRequest.setAppId(AlipayConfig.APP_ID);
        certAlipayRequest.setPrivateKey(AlipayConfig.MERCHANT_PRIVATE_KEY);
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset(AlipayConfig.CHARSET);
        certAlipayRequest.setSignType(AlipayConfig.SIGN_TYPE);

        certAlipayRequest.setCertPath(path + AlipayConfig.app_cert_path);
        certAlipayRequest.setAlipayPublicCertPath(path + AlipayConfig.alipay_cert_path);
        certAlipayRequest.setRootCertPath(path + AlipayConfig.alipay_root_cert_path);

        try {
            return new DefaultAlipayClient(certAlipayRequest);
        } catch (AlipayApiException e) {
            log.error("初始化支付宝客户端失败", e);
            return null;
        }
    }

}