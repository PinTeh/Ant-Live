package cn.imhtb.live.modules.live.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.common.annotation.IgnoreToken;
import cn.imhtb.live.common.enums.DisabledStatusEnum;
import cn.imhtb.live.common.enums.PresentRewardTypeEnum;
import cn.imhtb.live.modules.live.service.IPresentService;
import cn.imhtb.live.modules.live.vo.PresentRespVo;
import cn.imhtb.live.modules.live.vo.RewardReqVo;
import cn.imhtb.live.pojo.database.Present;
import cn.imhtb.live.pojo.vo.request.SendPresentRequest;
import cn.imhtb.live.service.IPresentRewardService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @IgnoreToken
    @ApiOperation("获取礼物列表")
    @GetMapping("/list")
    public ApiResponse<List<PresentRespVo>> list() {
        List<Present> presentList = presentService.list(new LambdaQueryWrapper<Present>()
                .eq(Present::getDisabled, DisabledStatusEnum.YES.getCode())
                .orderByAsc(Present::getSort));
        return ApiResponse.ofSuccess(BeanUtil.copyToList(presentList, PresentRespVo.class));
    }

    @ApiOperation("赠送礼物")
    @PostMapping("/live/reward")
    public ApiResponse<?> live(@RequestBody SendPresentRequest sendPresentRequest) {
        String reward = presentRewardService.createReward(sendPresentRequest.getPid(), sendPresentRequest.getRid(), sendPresentRequest.getNumber(), PresentRewardTypeEnum.LIVE.getCode());
        return StringUtils.isEmpty(reward) ? ApiResponse.ofSuccess() : ApiResponse.ofError(null, reward);
    }

    @ApiOperation("赠送礼物")
    @PostMapping("/reward")
    public ApiResponse<Boolean> reward(@RequestBody @Valid RewardReqVo rewardReqVo) {
        presentRewardService.createReward(rewardReqVo);
        return ApiResponse.ofSuccess();
    }

}
