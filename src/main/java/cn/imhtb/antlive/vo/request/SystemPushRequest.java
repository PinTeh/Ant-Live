package cn.imhtb.antlive.vo.request;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author PinTeh
 * @date 2020/5/8
 */
@Data
public class SystemPushRequest  {

    private Integer id;

    private String email;

    private String mobile;

    private Integer open;

    private String listenerItems;

}
