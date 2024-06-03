package cn.imhtb.live.modules.server;

import cn.imhtb.live.pojo.User;
import com.alibaba.fastjson.JSON;
import lombok.*;

/**
 * @author PinTeh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebMessage {

    private int c;

    private U u;

    private P p;

    private Object d;

    private String op;

    public WebMessage(int c, Object d, String op) {
        this.c = c;
        this.d = d;
        this.op = op;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class U {
        private Integer id;
        private String name;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class P {
        private Integer id;
        private String name;
        private String icon;
        private Integer number;
    }

    public static WebMessage createChat(User user, String message) {
        WebMessage webMessage = new WebMessage();
        webMessage.setU(new U(user.getId(), user.getNickname()));
        webMessage.setOp("MESSAGE");
        webMessage.setD(message);
        return webMessage;
    }

    public static WebMessage createPresent(User user, P p) {
        WebMessage webMessage = new WebMessage();
        webMessage.setU(new U(user.getId(), user.getNickname()));
        webMessage.setOp("PRESENT");
        webMessage.setP(p);
        webMessage.setD(user.getNickname() + "赠送了" + p.getName() + "礼物");
        return webMessage;
    }

}
