package cn.imhtb.live.controller;

import cn.imhtb.live.common.ApiResponse;
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

    @ApiOperation("保存直播间关注关系")
    @PostMapping("/relation/save")
    public ApiResponse<Boolean> saveRelation(@RequestBody @Valid RelationSaveRequest request){
        return ApiResponse.ofSuccess(watchService.saveRelation(request));
    }

    @ApiOperation("获取关注、历史记录")
    @GetMapping("/list")
    public ApiResponse<?> listWatches(@RequestParam Integer type
            , @RequestParam(required = false, defaultValue = "10") Integer limit
            , @RequestParam(required = false, defaultValue = "1") Integer page) {
        return ApiResponse.ofSuccess(watchService.listWatches(type, limit, page));
    }

    @ApiOperation("清除历史记录")
    @PostMapping("/history/clear")
    public ApiResponse<Boolean> clearHistory(){
        return ApiResponse.ofSuccess(watchService.clearHistory());
    }

}
