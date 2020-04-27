package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.config.AlipayConfig;
import cn.imhtb.antlive.entity.Bill;
import cn.imhtb.antlive.entity.database.Withdrawal;
import cn.imhtb.antlive.service.IAlipayService;
import cn.imhtb.antlive.service.IBillService;
import cn.imhtb.antlive.service.IMailService;
import cn.imhtb.antlive.service.IWithdrawalService;
import cn.imhtb.antlive.utils.DecimalUtils;
import cn.imhtb.antlive.utils.JwtUtils;
import cn.imhtb.antlive.vo.request.WithdrawalRequest;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author PinTeh
 */
@Slf4j
@RestController
@RequestMapping("/bill")
public class BillController {

    private final IAlipayService alipayService;

    private final IBillService billService;

    private final IWithdrawalService withdrawalService;

    public BillController(IAlipayService alipayService, IBillService billService,IWithdrawalService withdrawalService) {
        this.alipayService = alipayService;
        this.billService = billService;
        this.withdrawalService = withdrawalService;
    }

    @PostMapping("/withdrawal")
    public ApiResponse withdrawal(@RequestBody WithdrawalRequest withdrawalRequest, HttpServletRequest request){
        Integer uid = JwtUtils.getId(request);
        String identity = "ihydlk5321@sandbox.com";
        String identityName = "沙箱环境";
        String no = UUID.randomUUID().toString().replaceAll("-", "").substring(10);
        try {
            alipayService.trans(no,withdrawalRequest.getVirtualAmount()
                    , String.valueOf(uid)
                    ,withdrawalRequest.getIdentity() == null ? identity : withdrawalRequest.getIdentity()
                    ,withdrawalRequest.getIdentityName() == null ? identityName : withdrawalRequest.getIdentityName());
        } catch (Exception e) {
            return ApiResponse.ofError("系统异常，提现失败");
        }
        return ApiResponse.ofSuccess("提现成功");
    }

    @GetMapping("/withdrawal/list")
    public ApiResponse withdrawalList(HttpServletRequest request,
                                      @RequestParam(required = false) String month,
                                      @RequestParam(required = false,defaultValue = "1") Integer page,
                                      @RequestParam(required = false,defaultValue = "10") Integer limit
                                      ){
        Integer uid = JwtUtils.getId(request);
        Page<Withdrawal> withdrawalPage = withdrawalService.page(
                new Page<>(page, limit)
                , new QueryWrapper<Withdrawal>()
                        .eq(!StringUtils.isEmpty(month),"DATE_FORMAT(create_time, '%Y-%m' )",month)
                        .eq("user_id", uid).orderByDesc("id"));
        return ApiResponse.ofSuccess(withdrawalPage);
    }

    @RequestMapping("create")
    public ApiResponse create(int money){
        if (money <= 0){
            return ApiResponse.ofError();
        }
        return ApiResponse.ofSuccess();
    }

    /**
     * get balance
     */
    @GetMapping()
    public ApiResponse balance(HttpServletRequest request){
        String token = request.getHeader(JwtUtils.getHeaderKey());
        Integer id = JwtUtils.getId(token);
        Bill bill = billService.getOne(new QueryWrapper<Bill>().eq("user_id", id).orderByDesc("id").last("limit 0,1"));
        if (bill!=null){
            return ApiResponse.ofSuccess(bill.getBalance());
        }
        return ApiResponse.ofSuccess(0);
    }

    /**
     * bill list
     */
    @GetMapping("/list")
    public ApiResponse list(HttpServletRequest request,@RequestParam(required = false) String month,@RequestParam(required = false,defaultValue = "10")Integer limit,@RequestParam(required = false,defaultValue = "1")Integer page){
        String token = request.getHeader(JwtUtils.getHeaderKey());
        Integer uid = JwtUtils.getId(token);
        QueryWrapper<Bill> wrapper = new QueryWrapper<Bill>()
                .eq("user_id", uid)
                .eq(!StringUtils.isEmpty(month),"DATE_FORMAT(create_time, '%Y-%m' )",month)
                .orderByDesc("id");
        Page<Bill> billPage = billService.page(new Page<>(page, limit), wrapper);
        return ApiResponse.ofSuccess(billPage);
    }

    @GetMapping(value = "/getPagePay",produces = "text/html")
    public String getPagePay() throws Exception{
        /* 模仿数据库，从后台调数据*/
        String no = UUID.randomUUID().toString().replaceAll("-", "").substring(10);
        String outTradeNo = "12123121514124";
        Integer totalAmount = 648;
        String subject = "和平精英点卷(为794409767购买)";

        String pay = alipayService.webPagePay(no, totalAmount, subject);
        System.out.println(pay);

        return pay;
    }

    @GetMapping(value = "/recharge")
    public ApiResponse recharge(String num,HttpServletRequest request) throws Exception{
        String no = UUID.randomUUID().toString().replaceAll("-", "").substring(10);
        String token = request.getHeader(JwtUtils.getHeaderKey());
        Claims claims = JwtUtils.verifyJWT(token);
        if (claims == null){
            return null;
        }
        String username = (String) claims.get("username");
        int totalAmount = Integer.parseInt(num);
        String subject = "Live 金豆充值(为" + username + "购买)";
        String pay = alipayService.webPagePay(no, totalAmount / 10, subject,claims.getId());
        return ApiResponse.ofSuccess("html",pay);
    }

    @RequestMapping(value = "/alipayNotifyNotice")
    public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {

        log.info("支付成功, 进入异步通知接口...");

        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用

            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");

            params.put(name, valueStr);
        }

        //调用SDK验证签名

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);


        //——请在这里编写您的程序（以下代码仅作参考）——

		/* 实际验证过程建议商户务必添加以下校验：
        1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
        if (signVerified) {

            //验证成功
            //商户订单号
            String outTradeNo = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

            //支付宝交易号
            String tradeNo = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

            //交易状态
            String tradeStatus = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

            //付款金额
            String totalAmount = new String(request.getParameter("total_amount").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

            String u = new String(request.getParameter("passback_params").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

            log.info("passback_params:" + u);

            if ("TRADE_FINISHED".equals(tradeStatus)) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                log.info("Trade finished");
            } else if ("TRADE_SUCCESS".equals(tradeStatus)) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
                log.info("passback_params decode:" + URLDecoder.decode(u,"utf-8"));
                Integer uid = Integer.parseInt(URLDecoder.decode(u,"utf-8"));
                Bill one = billService.getOne(new QueryWrapper<Bill>().eq("user_id", uid).orderByDesc("id").last("limit 0,1"));
                Bill bill = new Bill();
                BigDecimal temp = new BigDecimal(totalAmount);
                //10倍金豆
                BigDecimal bigDecimal = temp.multiply(new BigDecimal(10));
                bill.setBillChange(bigDecimal);
                bill.setUserId(uid);
                if (one == null){
                    bill.setBalance(bigDecimal);
                }else {
                    bill.setBalance(bigDecimal.add(one.getBalance()));
                }
                bill.setBillChange(bigDecimal);
                bill.setType(0);
                bill.setUpdateTime(LocalDateTime.now());
                bill.setCreateTime(LocalDateTime.now());
                bill.setOrderNo(outTradeNo);
                billService.save(bill);

                //这里不用 查  只是为了 看日志 查的方法应该卸载 同步回调 页面 中

                log.info("********************** 支付成功(支付宝异步通知)查询 只是为了 看日志  **********************");
                log.info("* 订单号: {}", outTradeNo);
                log.info("* 支付宝交易号: {}", tradeNo);
                log.info("* 实付金额: {}", totalAmount);

                //log.info("* 购买产品: {}", product.getName());
                log.info("***************************************************************");

            }
            log.info("支付成功...");

        } else {//验证失败
            log.info("支付, 验签失败...");
        }

        return "success";
    }

    
}
