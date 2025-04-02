package cn.imhtb.live.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author pinteh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("分页数据响应")
public class PageWrapperResp<T> {

    @ApiModelProperty("总数")
    private long total;

    @ApiModelProperty("列表数据")
    private List<T> list;

}
