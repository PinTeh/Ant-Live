package cn.imhtb.live.controller.admin;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.database.Room;
import cn.imhtb.live.pojo.database.BanRecord;
import cn.imhtb.live.pojo.database.LiveDetect;
import cn.imhtb.live.service.IBanRecordService;
import cn.imhtb.live.service.ILiveDetectService;
import cn.imhtb.live.service.IRoomService;
import cn.imhtb.live.service.ITencentLiveService;
import cn.imhtb.live.pojo.vo.request.LiveBanRequest;
import cn.imhtb.live.pojo.vo.response.LiveBanResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencentcloudapi.live.v20180801.models.DescribeLiveForbidStreamListResponse;
import com.tencentcloudapi.live.v20180801.models.ForbidStreamInfo;
import com.tencentcloudapi.live.v20180801.models.ModifyLiveSnapshotTemplateRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author PinTeh
 * @date 2020/5/9
 */
@RestController
@RequestMapping("/admin/live")
@PreAuthorize("hasAnyRole('ROLE_ROOT','ROLE_LIVE')")
public class AdminLiveController {


    private final IRoomService roomService;

    private final IBanRecordService banRecordService;

    private final ILiveDetectService liveDetectService;

    private final ITencentLiveService tencentLiveService;


    public AdminLiveController(ITencentLiveService tencentLiveService, IRoomService roomService, ILiveDetectService liveDetectService, IBanRecordService banRecordService) {
        this.tencentLiveService = tencentLiveService;
        this.roomService = roomService;
        this.liveDetectService = liveDetectService;
        this.banRecordService = banRecordService;
    }

    @GetMapping("/detect/list")
    public ApiResponse liveDetectList(@RequestParam(required = false) Integer rid, @RequestParam(defaultValue = "1", required = false) Integer page, @RequestParam(defaultValue = "10", required = false) Integer limit) {
        Page<LiveDetect> liveDetectPage = liveDetectService.page(new Page<>(page, limit), new QueryWrapper<LiveDetect>().eq(rid != null, "room_id", rid).orderByDesc("id"));
        return ApiResponse.ofSuccess(liveDetectPage);
    }

    @GetMapping("/ban/record/list")
    public ApiResponse banRecordList(@RequestParam(defaultValue = "1", required = false) Integer page, @RequestParam(defaultValue = "10", required = false) Integer limit) {
        Page<BanRecord> liveDetectPage = banRecordService.page(new Page<>(page, limit), new QueryWrapper<BanRecord>().orderByDesc("id"));
        return ApiResponse.ofSuccess(liveDetectPage);
    }

    /**
     * 获取鉴黄模板列表
     */
    @GetMapping("/snapshot/template/list")
    public ApiResponse snapshotTemplatesList() {
        return ApiResponse.ofSuccess(tencentLiveService.snapshotTemplatesList());
    }

    @PostMapping("/snapshot/template/update")
    public ApiResponse snapshotTemplatesUpdate(@RequestBody ModifyLiveSnapshotTemplateRequest request) {
        tencentLiveService.snapshotTemplatesUpdate(request);
        return ApiResponse.ofSuccess();
    }


    /**
     * 调用腾讯云获取封禁列表
     * {
     * "Response": {
     * "RequestId": "1369339a-d886-47b8-bfe0-5c0fd56269e4",
     * "TotalNum": 1,
     * "TotalPage": 1,
     * "PageNum": 1,
     * "PageSize": 10,
     * "ForbidStreamList": [
     * {
     * "StreamName": "1",
     * "ExpireTime": "2020-04-21 17:55:57",
     * "CreateTime": "2020-04-14 17:55:57"
     * }
     * ]
     * }
     * }
     */
    @GetMapping("/ban/list")
    public ApiResponse banList(@RequestParam(defaultValue = "1", required = false) Integer page, @RequestParam(defaultValue = "10", required = false) Integer limit) {
        DescribeLiveForbidStreamListResponse response = tencentLiveService.banList(page, limit);
        ForbidStreamInfo[] forbidStreamList = response.getForbidStreamList();
        List<LiveBanResponse> responses = new ArrayList<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (ForbidStreamInfo forbidStreamInfo : forbidStreamList) {
            String streamName = forbidStreamInfo.getStreamName();
            Room r = roomService.getById(Integer.valueOf(streamName));
            LiveBanResponse liveBanResponse = LiveBanResponse.builder()
                    .roomId(r.getId())
                    .userId(r.getUserId())
                    .reason("封禁原因")
                    .resumeTime(LocalDateTime.parse(forbidStreamInfo.getExpireTime(), df))
                    .createTime(LocalDateTime.parse(forbidStreamInfo.getCreateTime(), df))
                    .status(r.getStatus())
                    .build();
            responses.add(liveBanResponse);
        }

        Map<String, Object> map = new HashMap<>(2);
        map.put("records", responses);
        map.put("total", response.getTotalNum());
        return ApiResponse.ofSuccess(map);
    }

    @PostMapping("/ban")
    public ApiResponse ban(@RequestBody LiveBanRequest request) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return tencentLiveService.ban(request.getRid(), request.getResumeTime(), request.getReason()) ? ApiResponse.ofSuccess() : ApiResponse.ofError();
    }

    @PostMapping("/resume")
    public ApiResponse resume(Integer rid) {
        return tencentLiveService.resume(rid) ? ApiResponse.ofSuccess() : ApiResponse.ofError();
    }

}
