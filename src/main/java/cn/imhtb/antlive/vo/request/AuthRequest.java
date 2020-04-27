package cn.imhtb.antlive.vo.request;

import lombok.Data;


@Data
public class AuthRequest {

    private int id;

    private int userId;

    private String realName;

    private String positiveUrl;

    private String reverseUrl;

    private String handUrl;

    private String cardNo;


}
