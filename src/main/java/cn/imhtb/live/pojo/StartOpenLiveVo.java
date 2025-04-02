package cn.imhtb.live.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("直播信息类")
public class StartOpenLiveVo {

    @ApiModelProperty("推流地址")
    private String pushUrl;

    @ApiModelProperty("推流密钥")
    private String secret;

    @ApiModelProperty("推流开始时间")
    private LocalDateTime startTime;

}
