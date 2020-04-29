package cn.imhtb.antlive.common;

public class Constants {

    public enum DisabledStatus{
        /**
         * 可用状态描述
         */
        YES(0,"可使用"),
        NO(1,"不可使用")
        ;
        private int code;

        private String desc;

        DisabledStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum LiveStatus{
        /**
         * 直播状态描述
         */
        UNAUTH(-1,"未认证"),
        STOP(0,"未开播"),
        LIVING(1,"正在直播中"),
        BANNING(2,"已被封禁")
        ;
        private int code;

        private String desc;

        LiveStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum LiveInfoStatus{
        /**
         * 直播信息状态描述
         */
        NO(0,"未完成"),
        YES(1,"完成"),
        ;
        private int code;

        private String desc;

        LiveInfoStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum AuthStatus{
        /**
         * 认证描述
         */
        NO(0,"未认证"),
        YES(1,"已认证");

        private int code;

        private String desc;

        AuthStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum PayPlatform{
        /**
         * 支付方式
         */
        ALIPAY("alipay","支付宝"),
        WECHAT("wechat","微信");

        private String code;

        private String desc;

        PayPlatform(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
