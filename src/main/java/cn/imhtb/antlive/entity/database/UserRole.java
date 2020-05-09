package cn.imhtb.antlive.entity.database;

import lombok.Builder;
import lombok.Data;

/**
 * @author PinTeh
 * @date 2020/5/9
 */
@Data
@Builder
public class UserRole {

    private Integer userId;

    private Integer roleId;
}
