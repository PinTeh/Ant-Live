package cn.imhtb.live.modules.system.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author pinteh
 * @date 2025/5/6
 */
@Data
public class SystemCategoryDetail {

    private Integer id;

    private Integer parentId;

    private String name;

    private String icon;

    private Integer sort;

    private Integer status;

    private Integer isDeleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
