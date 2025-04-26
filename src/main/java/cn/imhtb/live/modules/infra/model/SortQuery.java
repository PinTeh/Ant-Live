package cn.imhtb.live.modules.infra.model;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author pinteh
 * @date 2025/4/14
 */
@Data
@ApiModel("排序字段")
public class SortQuery {

    @ApiModelProperty(value = "排序字段", example = "sort=createTime,desc&sort=updateTime,asc")
    private String[] sortQuery;

    public Sort getSort(){
        if (ArrayUtil.isEmpty(sortQuery)){
            return Sort.unsorted();
        }
        List<Sort.Order> orders = new ArrayList<>();
        // eg: sortQuery=createTime,desc&sortQuery=updateTime,asc
        // sortQuery[0] = createTime,desc sortQuery[1] = updateTime,desc
        if (CharSequenceUtil.contains(sortQuery[0], ",")){
            for (String sortStr : sortQuery) {
                String[] split = sortStr.split(",");
                if (split.length == 2){
                    // 转换排序方向
                    Optional<Sort.Direction> direction = Sort.Direction.fromOptionalString(split[1].toUpperCase());
                    direction.ifPresent(d -> orders.add(new Sort.Order(d, split[0])));
                }
            }
        }else {
            // eg: sortQuery=createTime,desc
            // sortQuery[0] = createTime, sortQuery[1] = desc
            Optional<Sort.Direction> direction = Sort.Direction.fromOptionalString(sortQuery[1].toUpperCase());
            direction.ifPresent(d -> orders.add(new Sort.Order(d, sortQuery[0])));
        }

        return Sort.by(orders);
    }

}
