package cn.imhtb.live.pojo.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pinteh
 * @date 2023/2/19
 */
@Data
@ApiModel(description = "拓展信息绑定")
public class BindExtraInfoRequestVO {

    @ApiModelProperty(value = "类型", allowableValues = "email,mobile")
    private String type;

    @ApiModelProperty("值")
    private String val;

    @ApiModelProperty("验证码")
    private String verifyCode;

}
