package cn.imhtb.live.modules.live.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author pinteh
 * @date 2024/9/14
 */
@Data
public class AuthReqVo {

    private int id;

    @ApiModelProperty("用户id")
    private int userId;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("身份证正面照片")
    private String positiveUrl;

    @ApiModelProperty("身份证反面照片")
    private String reverseUrl;

    @ApiModelProperty("手持身份证照片")
    private String handUrl;

    @ApiModelProperty("证件号码")
    private String cardNo;

}
