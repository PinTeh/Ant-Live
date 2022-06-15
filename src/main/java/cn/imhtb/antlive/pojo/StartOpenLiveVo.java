package cn.imhtb.antlive.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("直播信息类")
public class StartOpenLiveVo {

    @ApiModelProperty("推流地址")
    private String pushUrl;

    @ApiModelProperty("推流密钥")
    private String secret;

}
