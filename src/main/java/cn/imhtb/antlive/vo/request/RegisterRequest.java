package cn.imhtb.antlive.vo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterRequest {

    private String account;

    private String password;

    private String passwordConfirm;

    private String code;

}
