package cn.imhtb.live.pojo.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @author pinteh
 */
@Getter
@Setter
@AllArgsConstructor
public class RegisterRequest {

    @NotEmpty(message = "用户名不能为空")
    @ApiModelProperty("登陆用户名")
    private String username;

    @NotEmpty(message = "昵称不能为空")
    @Length(max = 10, min = 2, message = "昵称长度在2～10之间")
    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("账号")
    private String account;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty("密码")
    @Length(min = 6, max = 24, message = "密码长度在6～24之间")
    private String password;

    @NotEmpty(message = "确认密码不能为空")
    @ApiModelProperty("确认密码")
    @Length(min = 6, max = 24, message = "确认密码长度在6～24之间")
    private String passwordConfirm;

    @ApiModelProperty("验证码")
    private String code;

}
