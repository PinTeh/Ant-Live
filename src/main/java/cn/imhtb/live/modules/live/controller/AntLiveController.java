package cn.imhtb.live.modules.live.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.modules.live.service.ILiveService;
import cn.imhtb.live.pojo.LiveStatusVo;
import cn.imhtb.live.pojo.StartOpenLiveVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 直播控制器
 *
 * @author pinteh
 * @since 2022/06/13
 */
@Api(tags = "直播接口")
@RestController
@RequestMapping("/api/v1/live")
public class AntLiveController {

    @Autowired
    @Qualifier("LalLiveService")
    private ILiveService liveService;

    @ApiOperation("申请直播密钥")
    @PostMapping("/applySecret")
    public ApiResponse<StartOpenLiveVo> applySecret() {
        return ApiResponse.ofSuccess(liveService.applySecret());
    }

    @ApiOperation("停止直播")
    @PostMapping("/stopLive")
    public ApiResponse<?> stopLive() {
        liveService.stopLive();
        return ApiResponse.ofSuccess();
    }

    @ApiOperation("获取直播状态")
    @GetMapping("/getLiveStatus")
    public ApiResponse<LiveStatusVo> getLiveStatus() {
        return ApiResponse.ofSuccess(liveService.getLiveStatus());
    }

}
