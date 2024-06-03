package cn.imhtb.live.pojo.vo.response;

import cn.imhtb.live.pojo.User;
import cn.imhtb.live.pojo.vo.FrontMenuItem;
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