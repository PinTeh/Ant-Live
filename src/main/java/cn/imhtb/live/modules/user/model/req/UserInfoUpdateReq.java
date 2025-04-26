package cn.imhtb.live.modules.user.model.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author PinTeh
 * @date 2020/5/6
 */
@Data
public class UserInfoUpdateReq {

    @Length(min = 1, max = 16, message = "昵称长度限制1～16位字符")
    private String nickName;

    @Length(max = 64, message = "最大签名长度限制64位字符")
    private String signature;

}
