package cn.imhtb.live.modules.system.model;

import lombok.Data;

/**
 * @author pinteh
 * @date 2025/5/6
 */
@Data
public class SystemCategoryUpdate {

    private Integer id;

    private Integer parentId;

    private String icon;

    private String name;

    private Integer sort;

    private Integer status;

    private Integer isDeleted;

}
