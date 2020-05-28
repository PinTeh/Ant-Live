package cn.imhtb.antlive.utils;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author PinTeh
 */
@Slf4j
@Service
public class SmsUtils {

    @Value("${tencent.sms.appid}")
    private  Integer appid;

    @Value("${tencent.sms.appkey}")
    private  String appkey;

    @Value("${tencent.sms.smsSign}")
    private  String smsSign;

    @Value("${tencent.sms.templateId}")
    private  Integer templateId;

    @Value("${tencent.sms.templateNoticeId}")
    private  Integer templateNoticeId;

    public void txSmsSend(String phoneNumber, ArrayList<String> params,String code) {

        Integer tid = "code".equals(code)?templateId:templateNoticeId;
        //封装短信应用码
        SmsSingleSender sender = new SmsSingleSender(appid,appkey);
        //地区，电话，模板ID，验证码，签名
        try {
            SmsSingleSenderResult result = sender.sendWithParam(
                    "86", phoneNumber,tid , params, smsSign, "", "");
            log.info("发送短信 - phone:{}，templateId:{}，params:{};result:{}",phoneNumber,tid,params,result);
            //{"result":0,"errmsg":"OK","ext":"","sid":"2106:39236780115826196758919298","fee":1}
            if (result.result != 0){
                log.error("发送短信服务异常，result:{}",result);
            }
        } catch (HTTPException | JSONException | IOException e) {
            log.error("发送短信服务异常,{}",e.getMessage());
        }
    }
}
