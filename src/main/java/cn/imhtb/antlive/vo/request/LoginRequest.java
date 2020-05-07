package cn.imhtb.antlive.vo.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginRequest {

    private String account;

    private String password;

}
