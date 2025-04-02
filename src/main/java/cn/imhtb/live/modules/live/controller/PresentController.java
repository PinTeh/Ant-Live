package cn.imhtb.live.modules.live.controller;

import cn.imhtb.live.annotation.NeedToken;
import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.enums.DisabledStatusEnum;
import cn.imhtb.live.enums.PresentRewardTypeEnum;
import cn.imhtb.live.modules.live.vo.RewardReqVo;
import cn.imhtb.live.pojo.Present;
import cn.imhtb.live.pojo.vo.request.SendPresentRequest;
import cn.imhtb.live.service.IPresentRewardService;
import cn.imhtb.live.modules.live.service.IPresentService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author PinTeh
 */
@Api(tags = "礼物接口")
@RestController
@RequestMapping("/api/v1/present")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PresentController {

    private final IPresentService presentService;
    private final IPresentRewardService presentRewardService;

    @ApiOperation("获取礼物列表")
    @GetMapping("/list")
    public ApiResponse<?> list() {
        return ApiResponse.ofSuccess(presentService.list(new LambdaQueryWrapper<Present>()
                .eq(Present::getDisabled, DisabledStatusEnum.YES.getCode())
                .orderByDesc(Present::getSort)));
    }

    @ApiOperation("赠送礼物")
    @PostMapping("/live/reward")
    public ApiResponse<?> live(@RequestBody SendPresentRequest sendPresentRequest) {
        String reward = presentRewardService.createReward(sendPresentRequest.getPid(), sendPresentRequest.getRid(), sendPresentRequest.getNumber(), PresentRewardTypeEnum.LIVE.getCode());
        return StringUtils.isEmpty(reward) ? ApiResponse.ofSuccess() : ApiResponse.ofError(null, reward);
    }

    @NeedToken
    @ApiOperation("赠送礼物")
    @PostMapping("/reward")
    public ApiResponse<Boolean> reward(@RequestBody RewardReqVo rewardReqVo) {
        presentRewardService.createReward(rewardReqVo);
        return ApiResponse.ofSuccess();
    }

}
