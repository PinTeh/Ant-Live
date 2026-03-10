package cn.imhtb.live.pojo.vo.request;

import lombok.Data;

/**
 * @author PinTeh
 * @date 2020/5/8
 */
@Data
public class SystemPushRequest {

    private Integer id;

    private String email;

    private String mobile;

    private Integer open;

    private String listenerItems;

}
