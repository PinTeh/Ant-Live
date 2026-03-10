package cn.imhtb.live.modules.system.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author pinteh
 * @date 2025/5/6
 */
@Data
public class SystemRoleDetail {

    private Integer id;

    private String name;

    private String permission;

    private Integer level;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
