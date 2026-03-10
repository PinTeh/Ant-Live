package cn.imhtb.live.pojo.tencent;

/**
 * @author PinTeh
 * @date 2020/4/14
 * type 图片类型， 0 ：正常图片， 1 ：色情图片， 2 ：性感图片， 3 ：涉政图片， 4 ：违法图片， 5 ：涉恐图片 ，6 - 9 ：其他其它图片
 */
public enum TenCentAppriseImageType {
    NORMAL(0, "正常图片"),
    SALACITY(1, "色情图片"),
    SEXY(2, "性感图片"),
    POLITICS(3, "涉政图片"),
    ILLEGAL(4, "违法图片"),
    HORRIBLE(5, "涉恐图片"),
    ELSE(6, "其他其它图片");

    private int type;

    private String desc;

    TenCentAppriseImageType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
