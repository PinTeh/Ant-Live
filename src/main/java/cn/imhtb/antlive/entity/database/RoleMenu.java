package cn.imhtb.antlive.entity.database;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author PinTeh
 * @date 2020/4/30
 */
@Data
@Builder
public class RoleMenu implements Serializable {

    private Integer roleId;

    private Integer menuId;
}
