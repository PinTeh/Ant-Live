package cn.imhtb.antlive.common;

public class Constants {

    public enum DisabledStatus{
        /**
         * 可用状态描述
         */
        YES(0,"可使用"),
        NO(1,"不可使用")
        ;
        private final int code;

        private final String desc;

        DisabledStatus(int code, String desc) {
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

    public enum LiveStatus{
        /**
         * 直播状态描述
         */
        UN_AUTH(-1,"未认证"),
        STOP(0,"未开播"),
        LIVING(1,"正在直播中"),
        BANNING(2,"已被封禁")
        ;
        private final int code;

        private final String desc;

        LiveStatus(int code, String desc) {
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

    public enum LiveInfoStatus{
        /**
         * 直播信息状态描述
         */
        NO(0,"未完成"),
        YES(1,"完成"),
        ;
        private final int code;

        private final String desc;

        LiveInfoStatus(int code, String desc) {
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

    public enum BillType{
        /**
         * 直播信息状态描述
         */
        INCOME(0,"收入"),
        OUTLAY(1,"支出"),
        ;
        private final int code;

        private final String desc;

        BillType(int code, String desc) {
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

    public enum BillMark{
        /**
         * 直播信息状态描述
         */
        LIVE_REWARD(0,"直播打赏"),
        VIDEO_REWARD(1,"视频打赏"),
        ;
        private final int code;

        private final String desc;

        BillMark(int code, String desc) {
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

    public enum AuthStatus{
        /**
         * 认证描述
         */
        NO(0,"等待认证"),
        YES(1,"认证通过"),
        REJECT(3,"认证失败"),
        AUTO_PASS(2,"智能审核通过");

        private final int code;

        private final String desc;

        AuthStatus(int code, String desc) {
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

    public enum PayPlatform{
        /**
         * 支付方式
         */
        ALIPAY("alipay","支付宝"),
        WECHAT("wechat","微信");

        private final String code;

        private final String desc;

        PayPlatform(String code, String desc) {
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

    public enum PresentRewardType{
        /**
         * 打赏类型
         */
        LIVE(0,"LIVE"),
        VIDEO(1,"VIDEO");

        private final Integer code;

        private final String desc;

        PresentRewardType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

    }
}
