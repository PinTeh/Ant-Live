package cn.imhtb.live.modules.base.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author pinteh
 * @date 2025/4/19
 */
@Data
@ApiModel("验证请求")
public class VerifyCodeReq {

    @NotNull
    @ApiModelProperty(value = "验证类型",allowableValues = "email,phone")
    private String verifyType;

    @NotNull
    @ApiModelProperty(value = "目标值", example = "xx@qq.com,187****2121")
    private String target;

}
