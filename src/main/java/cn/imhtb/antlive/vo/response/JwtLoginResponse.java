package cn.imhtb.antlive.vo.response;

import cn.imhtb.antlive.entity.JwtUser;
import cn.imhtb.antlive.entity.User;
import cn.imhtb.antlive.vo.FrontMenuItem;
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

    private JwtUser user;

    private List<FrontMenuItem> menu;

    public JwtLoginResponse(String token, JwtUser user) {
        this.token = token;
        this.user = user;
    }
}
