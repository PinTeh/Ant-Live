package cn.imhtb.antlive.vo.response;

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
public class LoginResponse {

    private String token;

    private User user;

    private List<FrontMenuItem> menu;

    public LoginResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
