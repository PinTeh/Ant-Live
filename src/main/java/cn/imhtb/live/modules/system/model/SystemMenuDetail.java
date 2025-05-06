package cn.imhtb.live.modules.system.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author pinteh
 * @date 2025/4/29
 */
@Data
public class SystemMenuDetail {

    private Integer id;

    private Integer menuIndex;

    private String icon;

    private String path;

    private String title;

    private Integer pid;

    private Integer sort;

    private Integer hidden;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
