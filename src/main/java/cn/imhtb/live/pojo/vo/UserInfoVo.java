package cn.imhtb.live.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author pinteh
 * @date 2023/5/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoVo {

    private Integer userId;

    private String username;

    private String nickName;

    private String avatar;

    private BigDecimal balance;

    private String signature;

    private List<Integer> roleIds;

}
