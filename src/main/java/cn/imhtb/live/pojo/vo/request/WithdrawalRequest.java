package cn.imhtb.live.pojo.vo.request;

import lombok.Data;

/**
 * @author PinTeh
 * @date 2020/4/9
 */
@Data
public class WithdrawalRequest {

    private Integer virtualAmount;

    private String identity;

    private String identityName;

}
