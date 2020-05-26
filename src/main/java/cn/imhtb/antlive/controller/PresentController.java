package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.entity.Present;
import cn.imhtb.antlive.service.IPresentService;
import cn.imhtb.antlive.service.IPresentRewardService;
import cn.imhtb.antlive.service.IRoomService;
import cn.imhtb.antlive.service.IVideoService;
import cn.imhtb.antlive.utils.JwtUtils;
import cn.imhtb.antlive.vo.request.SendPresentRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author PinTeh
 */
@RestController
@RequestMapping("/present")
public class PresentController {

    private final IPresentService presentService;

    private final IPresentRewardService presentRewardService;

    public PresentController(IPresentService presentService, IPresentRewardService presentRewardService) {
        this.presentService = presentService;
        this.presentRewardService = presentRewardService;
    }

    @GetMapping()
    public ApiResponse list(){
        List<Present> list = presentService.list(new QueryWrapper<Present>().eq("disabled", Constants.DisabledStatus.YES.getCode()).orderByDesc("sort"));
        return ApiResponse.ofSuccess(list);
    }

    /**
     * reward presents
     */
    @PostMapping("/live/reward")
    public ApiResponse live(@RequestBody SendPresentRequest sendPresentRequest, HttpServletRequest request){
        Integer uid = JwtUtils.getId(request);
        return presentRewardService.createReward(uid, sendPresentRequest.getPid(), sendPresentRequest.getRid(), sendPresentRequest.getNumber(),Constants.PresentRewardType.LIVE.getCode())?ApiResponse.ofSuccess():ApiResponse.ofError();
    }

    @PostMapping("/video/reward")
    public ApiResponse video(@RequestBody SendPresentRequest sendPresentRequest, HttpServletRequest request){
        Integer uid = JwtUtils.getId(request);
        return presentRewardService.createReward(uid,
                sendPresentRequest.getPid(),
                sendPresentRequest.getVid(),
                sendPresentRequest.getNumber(),
                Constants.PresentRewardType.VIDEO.getCode())?ApiResponse.ofSuccess():ApiResponse.ofError();
    }
}
