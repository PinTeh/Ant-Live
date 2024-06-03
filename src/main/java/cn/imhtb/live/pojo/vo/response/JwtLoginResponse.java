package cn.imhtb.live.pojo.vo.response;

import cn.imhtb.live.pojo.vo.FrontMenuItem;
import cn.imhtb.live.pojo.vo.UserInfoVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author PinTeh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtLoginResponse {

    private String token;

    private UserInfoVo user;

    private List<FrontMenuItem> menu;

    public JwtLoginResponse(String token, UserInfoVo user) {
        this.token = token;
        this.user = user;
    }

}
