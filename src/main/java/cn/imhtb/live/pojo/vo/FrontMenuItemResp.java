package cn.imhtb.live.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author PinTeh
 * @date 2020/4/22
 */
@Data
public class FrontMenuItemResp implements Serializable {

    private Integer id;

    @ApiModelProperty(value = "菜单索引")
    private Integer index;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "菜单标题")
    private String label;

    @ApiModelProperty(value = "是否隐藏")
    private Integer hidden;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "父级id")
    private Integer pid;

    @ApiModelProperty(value = "子菜单")
    private List<FrontMenuItemResp> children;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
