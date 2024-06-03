package cn.imhtb.live.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页数据
 *
 * @author pinteh
 * @date 2023/2/19
 */
@Data
@ApiModel("分页数据")
public class PageData<T> {

    @ApiModelProperty("总数")
    private long total;

    @ApiModelProperty("数据")
    private List<T> list;

}
