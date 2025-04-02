package cn.imhtb.live.modules.live.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pinteh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRespVo {

    @ApiModelProperty("房间ID")
    private Integer id;

    @ApiModelProperty("直播间名称")
    private String title;

    @ApiModelProperty("直播间封面")
    private String cover;

    @ApiModelProperty("直播推流url")
    private String pullUrl;

    @ApiModelProperty("直播间状态")
    private Integer status;

    @ApiModelProperty("主播信息")
    private UserInfoVo userInfo;

    @ApiModelProperty("直播间分类")
    private CategoryInfoVo categoryInfo;

    @Data
    @AllArgsConstructor
    public static class UserInfoVo {
        private Integer id;
        private String name;
        private String avatar;
    }

    @Data
    @AllArgsConstructor
    public static class CategoryInfoVo {
        private Integer id;
        private String name;
    }

}
