package cn.imhtb.antlive.vo.request;

import lombok.Data;

/**
 * @author PinTeh
 * @date 2020/3/25
 */
@Data
public class SendPresentRequest {

    private Integer pid;

    private Integer number;

    private Integer rid;

    private Integer vid;
}
