package cn.imhtb.antlive.service;

public interface IAlipayService {

    String webPagePay(String outTradeNo,Integer totalAmount,String subject) throws Exception;

    String webPagePay(String outTradeNo,Integer totalAmount,String subject,String u) throws Exception;

    void trans(String outTradeNo, Integer virtualAmount,String u,String identity,String identityName)  throws Exception;
}
