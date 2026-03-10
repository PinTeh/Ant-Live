package cn.imhtb.live.modules.system.model;

import lombok.Data;

/**
 * @author pinteh
 * @date 2025/5/6
 */
@Data
public class SystemRoleUpdate {

    private Integer id;

    private String name;

    private String permission;

    private Integer level;

}
