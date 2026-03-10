package cn.imhtb.live.modules.live.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pinteh
 * @date 2024/9/14
 */
@Data
public class AuthRespVo {

    private int id;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("状态")
    private String statusName;

}
