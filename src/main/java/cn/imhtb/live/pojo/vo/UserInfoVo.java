package cn.imhtb.live.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author pinteh
 * @date 2023/5/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoVo {

    private Integer userId;

    private String username;

    private String nickName;

    private String avatar;

    @ApiModelProperty("开心果余额")
    private BigDecimal balance;

    @ApiModelProperty("个性签名")
    private String signature;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机")
    private String mobile;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("是否进行实名认证")
    private Boolean hasAuth;

    private List<Integer> roleIds;

}
