package cn.imhtb.live.modules.live.vo;

import cn.imhtb.live.modules.infra.model.PageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author pinteh
 * @date 2025/6/17
 */
@Setter
@Getter
@ApiModel("直播记录查询条件")
public class LiveInfoReqVo extends PageQuery {
}
