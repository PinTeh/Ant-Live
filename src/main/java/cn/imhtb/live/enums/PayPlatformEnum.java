package cn.imhtb.live.enums;

public enum PayPlatformEnum {
    /**
     * 支付方式
     */
    ALIPAY("alipay", "支付宝"),
    WECHAT("wechat", "微信");

    private final String code;

    private final String desc;

    PayPlatformEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
