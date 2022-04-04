package cn.imhtb.antlive.common;

public enum  PayType  {
    /**
     * 支付类型
     */
    ALI("支付宝",1),
    WECHAT("微信",2);

    private int code;
    private String name;

    public static String getName(int code,String name) {
        for (PayType c : PayType.values()) {
            if (c.getCode() == code) {
                return c.name;
            }
        }
        return null;
    }

    PayType(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
