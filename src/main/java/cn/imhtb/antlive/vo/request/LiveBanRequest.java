package cn.imhtb.antlive.vo.request;

import lombok.Data;


/**
 * @author PinTeh
 * @date 2020/5/27
 */
@Data
public class LiveBanRequest {

    private Integer rid;

    private String resumeTime;

    private String reason;
}
