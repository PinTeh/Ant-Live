package cn.imhtb.antlive.vo.request;

import lombok.Data;

import java.util.List;

/**
 * @author PinTeh
 * @date 2020/5/1
 */
@Data
public class RoleMenuUpdateRequest {

    private Integer roleId;

    private List<Integer> menuIds;
}
