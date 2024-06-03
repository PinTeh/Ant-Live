package cn.imhtb.live.controller.admin;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.database.Video;
import cn.imhtb.live.service.IVideoService;
import cn.imhtb.live.pojo.vo.request.IdsRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.vod.v20180717.VodClient;
import com.tencentcloudapi.vod.v20180717.models.SearchMediaRequest;
import com.tencentcloudapi.vod.v20180717.models.SearchMediaResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @author PinTeh
 * @date 2020/5/27
 */
@RestController
@RequestMapping("/admin/video")
public class AdminVideoController {

    private final IVideoService videoService;

    private final VodClient vodClient;

    public AdminVideoController(IVideoService videoService, VodClient vodClient) {
        this.videoService = videoService;
        this.vodClient = vodClient;
    }

    @GetMapping("/list")
    public ApiResponse list(@RequestParam(required = false, defaultValue = "10") Integer limit
            , @RequestParam(required = false, defaultValue = "1") Integer page
            , @RequestParam(required = false) Integer status
            , @RequestParam(required = false) String key) {
        Page<Video> videoPage = videoService.page(new Page<>(page, limit),
                new QueryWrapper<Video>().orderByDesc("id")
                        .eq(status != null, "status", status)
                        .like(key != null, "user_id", key)
                        .or()
                        .like(key != null, "file_id", key)
                        .or()
                        .like(key != null, "title", key));
        return ApiResponse.ofSuccess(videoPage);
    }

    @GetMapping("/vod/list")
    public ApiResponse vodList(@RequestParam(required = false, defaultValue = "10") Integer limit
            , @RequestParam(required = false, defaultValue = "1") Integer page) throws TencentCloudSDKException {
        SearchMediaRequest request = new SearchMediaRequest();
        request.setOffset((long) ((page - 1) * limit));
        request.setLimit(limit.longValue());
        SearchMediaResponse response = vodClient.SearchMedia(request);
        return ApiResponse.ofSuccess(response);
    }

    @PostMapping("/edit")
    public ApiResponse edit(@RequestBody Video video) {
        video.setCreateTime(null);
        video.setUpdateTime(null);
        videoService.saveOrUpdate(video);
        return ApiResponse.ofSuccess("Edit success");
    }

    @PostMapping("/del")
    public ApiResponse del(@RequestBody IdsRequest request) {
        try {
            videoService.removeByIds(Arrays.asList(request.getIds()));
            return ApiResponse.ofSuccess("Delete success");
        } catch (Exception e) {
            return ApiResponse.ofSuccess("Delete fail");
        }
    }
}
