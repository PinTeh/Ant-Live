package cn.imhtb.antlive.common;

public enum ResponseCode {
    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    UN_KNOW_ERROR(2,"UN_KNOW_ERROR"),
    UN_AUTH_ERROR(401,"UN_AUTH_ERROR"),
    ;


    private int code;
    private String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
