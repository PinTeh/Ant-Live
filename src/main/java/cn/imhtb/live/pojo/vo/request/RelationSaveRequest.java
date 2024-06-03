package cn.imhtb.live.pojo.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author pinteh
 * @date 2023/5/31
 */
@Data
public class RelationSaveRequest {

    @NotNull
    private Integer rid;

    @NotNull
    @ApiModelProperty("操作类型, 1 关注 2 取消关注 3 历史记录")
    private Integer act;

}
