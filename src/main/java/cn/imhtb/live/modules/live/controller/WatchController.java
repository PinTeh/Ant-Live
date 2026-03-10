package cn.imhtb.live.modules.live.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.common.holder.UserHolder;
import cn.imhtb.live.pojo.vo.request.RelationSaveRequest;
import cn.imhtb.live.service.IWatchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author PinTeh
 * @date 2020/3/18
 */
@Api(tags = "关注历史接口")
@RestController
@RequestMapping("/api/v1/watch")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WatchController {

    private final IWatchService watchService;

    @ApiOperation("获取关注、历史记录")
    @GetMapping("/list")
    public ApiResponse<?> listWatches(@RequestParam Integer type
            , @RequestParam(required = false, defaultValue = "10") Integer limit
            , @RequestParam(required = false, defaultValue = "1") Integer page) {
        return ApiResponse.ofSuccess(watchService.listWatches(UserHolder.getUserId(), type, limit, page));
    }

    @ApiOperation("历史记录")
    @PostMapping("/history/save")
    public ApiResponse<Boolean> saveHistory(@RequestBody @Valid RelationSaveRequest request){
        return ApiResponse.ofSuccess(watchService.saveHistory(UserHolder.getUserId(), request.getRoomId()));
    }

    @ApiOperation("关注直播间")
    @PostMapping("/follow")
    public ApiResponse<Boolean> saveFollow(@RequestBody @Valid RelationSaveRequest request){
        return ApiResponse.ofSuccess(watchService.follow(UserHolder.getUserId(), request.getRoomId()));
    }

    @ApiOperation("取消关注")
    @PostMapping("/unFollow")
    public ApiResponse<Boolean> unFollow(@RequestBody @Valid RelationSaveRequest request){
        return ApiResponse.ofSuccess(watchService.unFollow(UserHolder.getUserId(), request.getRoomId()));
    }

    @ApiOperation("清除历史记录")
    @PostMapping("/history/clear")
    public ApiResponse<Boolean> clearHistory(){
        return ApiResponse.ofSuccess(watchService.clearHistory(UserHolder.getUserId()));
    }

}
