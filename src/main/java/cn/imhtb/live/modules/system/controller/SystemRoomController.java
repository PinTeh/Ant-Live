package cn.imhtb.live.modules.system.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.common.PageData;
import cn.imhtb.live.modules.infra.controller.AbstractBaseController;
import cn.imhtb.live.modules.infra.model.PageQuery;
import cn.imhtb.live.modules.system.model.SystemRoomDetail;
import cn.imhtb.live.modules.system.model.SystemRoomQuery;
import cn.imhtb.live.modules.system.model.SystemRoomUpdate;
import cn.imhtb.live.modules.system.service.ISystemRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pinteh
 * @date 2025/12/07
 */
@Api(tags = "系统_直播间管理接口")
@RequestMapping("/api/v1/system/room")
@RestController
public class SystemRoomController extends AbstractBaseController<ISystemRoomService, SystemRoomDetail, SystemRoomQuery, SystemRoomDetail, SystemRoomUpdate> {

    private final ISystemRoomService systemRoomService;

    public SystemRoomController(ISystemRoomService systemRoomService) {
        this.systemRoomService = systemRoomService;
    }

    /**
     * 重写分页查询方法，返回包含主播信息的直播间数据
     */
    @Override
    @ApiOperation("获取分页数据（包含主播信息）")
    @GetMapping("/page")
    public ApiResponse<PageData<SystemRoomDetail>> page(SystemRoomQuery query, PageQuery pageQuery) {
        return ApiResponse.ofSuccess(systemRoomService.page(query, pageQuery));
    }
}
