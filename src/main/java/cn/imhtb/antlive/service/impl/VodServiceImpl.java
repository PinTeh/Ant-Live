package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.service.IVodService;
import cn.imhtb.antlive.utils.TencentUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author PinTeh
 * @date 2020/5/25
 */
@Service
public class VodServiceImpl implements IVodService {

    @Value("${tencent.live.iii}")
    private String iii;

    @Value("${tencent.live.kkk}")
    private String kkk;

    @Override
    public String signature() {
        TencentUtils sign = new TencentUtils();
        sign.setSecretId(iii);
        sign.setSecretKey(kkk);
        sign.setCurrentTime(System.currentTimeMillis() / 1000);
        sign.setRandom(new Random().nextInt(java.lang.Integer.MAX_VALUE));
        sign.setSignValidDuration(3600 * 24 * 2);

        try {
            String signature = sign.getUploadSignature();
            System.out.println("signature : " + signature);
            return signature;
        } catch (Exception e) {
            System.out.print("获取签名失败");
            e.printStackTrace();
        }
        return null;
    }
}
