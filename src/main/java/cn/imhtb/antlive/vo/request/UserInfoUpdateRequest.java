package cn.imhtb.antlive.vo.request;

import lombok.Data;

/**
 * @author PinTeh
 * @date 2020/5/6
 */
@Data
public class UserInfoUpdateRequest {

    private String nickName;

    private String avatar;

    private String signature;
}
