package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.modules.live.ILiveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 直播控制器
 *
 * @author pinteh
 * @since  2022/06/13
 */
@RestController
@RequestMapping("/ant/live")
public class AntLiveController {

    @Resource
    private ILiveService liveService;

    /**
     * 申请直播密钥
     *
     * @return {@link ApiResponse}
     */
    @PostMapping("/applySecret")
    public ApiResponse applySecret(HttpServletRequest request){
        return ApiResponse.ofSuccess(liveService.applySecret(request));
    }

    /**
     * 停止直播
     *
     * @param request 请求
     * @return {@link ApiResponse}
     */
    @PostMapping("/stopLive")
    public ApiResponse stopLive(HttpServletRequest request){
        liveService.stopLive(request);
        return ApiResponse.ofSuccess();
    }

    /**
     * 获取直播状态
     *
     * @param request 请求
     * @return {@link ApiResponse}
     */
    @GetMapping("/getLiveStatus")
    public ApiResponse getLiveStatus(HttpServletRequest request){
        return ApiResponse.ofSuccess(liveService.getLiveStatus(request));
    }


}
