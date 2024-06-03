package cn.imhtb.live.pojo.vo.request;

import lombok.Data;

import java.util.List;

/**
 * @author PinTeh
 * @date 2020/5/1
 */
@Data
public class UserRoleUpdateRequest {

    private Integer userId;

    private List<Integer> roleIds;
}
