package cn.imhtb.live.service.impl;

import cn.imhtb.live.config.AlipayConfig;
import cn.imhtb.live.pojo.Bill;
import cn.imhtb.live.pojo.database.Withdrawal;
import cn.imhtb.live.enums.PayPlatformEnum;
import cn.imhtb.live.mappers.BillMapper;
import cn.imhtb.live.mappers.WithdrawalMapper;
import cn.imhtb.live.service.IAlipayService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;

/**
 * @author PinTeh
 */
@Slf4j
@Service
public class AlipayServiceImpl implements IAlipayService {

    private final BillMapper billMapper;

    private final WithdrawalMapper withdrawalMapper;

    @Autowired(required = false)
    private DefaultAlipayClient client;

    public AlipayServiceImpl(BillMapper billMapper, WithdrawalMapper withdrawalMapper) {
        this.billMapper = billMapper;
        this.withdrawalMapper = withdrawalMapper;
    }

    @Override
    public String webPagePay(String outTradeNo, Integer totalAmount, String subject) throws Exception {
        return null;
    }

    @Override
    public String webPagePay(String outTradeNo, Integer totalAmount, String subject, String u) throws Exception {
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        /* 同步通知，支付完成后，支付成功页面*/
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        /* 异步通知，支付完成后，需要进行的异步处理*/
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        log.info("[alipay] 支付宝请求uid:" + u);
        String encode = URLEncoder.encode(u, "utf-8");
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + outTradeNo + "\","
                + "\"total_amount\":\"" + totalAmount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"商品名称\","
                + "\"timeout_express\":\"90m\","
                + "\"passback_params\":\"" + encode + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        /*转换格式*/
        return client.pageExecute(alipayRequest).getBody().replace('\"', '\'').replace('\n', ' ');
    }

    @Override
    public void trans(String outTradeNo, Integer virtualAmount, String uid, String identity, String identityName) throws AlipayApiException {

        /* 开心果 与 金豆 转换率 10:1  提现比例 2 : 1 */
        int realTransAmount = Math.abs(virtualAmount / 10 / 2);
        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        request.setBizContent("{" +
                "\"out_biz_no\":\"" + outTradeNo + "\"," +
                "\"trans_amount\":" + realTransAmount + "," +
                "\"product_code\":\"TRANS_ACCOUNT_NO_PWD\"," +
                "\"biz_scene\":\"DIRECT_TRANSFER\"," +
                "\"order_title\":\"Live直播开心果提现\"," +
                "\"payee_info\":{" +
                "\"identity\":\"" + identity + "\"," +
                "\"identity_type\":\"ALIPAY_LOGON_ID\"," +
                "\"name\":\"" + identityName + "\"," +
                "    }," +
                "\"remark\":\"Live直播提现备注\"," +
                "\"business_params\":\"{\\\"payer_show_name\\\":\\\"PinTeh Live\\\"}\"," +
                "  }");
        AlipayFundTransUniTransferResponse response = client.certificateExecute(request);
        if (response.isSuccess()) {
            // Bill (insert record)
            Bill last = billMapper.selectOne(new QueryWrapper<Bill>().eq("user_id", uid).orderByDesc("id").last("limit 0,1"));
            BigDecimal virtualBigDecimal = new BigDecimal(virtualAmount);
            BigDecimal ret = last.getBalance().subtract(virtualBigDecimal);
            if (ret.compareTo(BigDecimal.ZERO) < 0) {
                // Balance not enough
                return;
            }
            Bill bill = new Bill();
            bill.setBillChange(virtualBigDecimal.negate());
            bill.setType(1);
            bill.setUserId(Integer.valueOf(uid));
            bill.setBalance(ret);
            bill.setMark("提现");
            bill.setOrderNo(outTradeNo);
            billMapper.insert(bill);
            log.info("[billMapper] insert success");
            // Settle account info (insert)
            Withdrawal withdrawal = new Withdrawal();
            withdrawal.setIdentity(identity);
            withdrawal.setIdentityName(identityName);
            withdrawal.setMark("提现");
            withdrawal.setStatus(1);
            withdrawal.setType(PayPlatformEnum.ALIPAY.getDesc());
            withdrawal.setVirtualAmount(virtualBigDecimal);
            withdrawal.setRealAmount(new BigDecimal(realTransAmount));
            withdrawal.setUserId(Integer.valueOf(uid));
            withdrawalMapper.insert(withdrawal);
            log.info("[withdrawalMapper] insert success");
            log.info("[alipay] call success");
        } else {
            log.info("[alipay] call fail");
        }
    }
}
