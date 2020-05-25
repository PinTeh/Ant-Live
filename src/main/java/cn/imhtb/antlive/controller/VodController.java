package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.entity.User;
import cn.imhtb.antlive.entity.database.Video;
import cn.imhtb.antlive.service.IUserService;
import cn.imhtb.antlive.service.IVideoService;
import cn.imhtb.antlive.service.IVodService;
import cn.imhtb.antlive.utils.JwtUtils;
import cn.imhtb.antlive.vo.request.VodSaveRequest;
import cn.imhtb.antlive.vo.response.VideoResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencentcloudapi.vod.v20180717.models.FileUploadTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author PinTeh
 * @date 2020/5/25
 */
@Slf4j
@RestController
@RequestMapping("/vod")
public class VodController {

    private final IVodService vodService;

    private final IVideoService videoService;

    private final IUserService userService;

    public VodController(IVodService vodService, IVideoService videoService, IUserService userService) {
        this.vodService = vodService;
        this.videoService = videoService;
        this.userService = userService;
    }

    @PostMapping("/signature")
    public ApiResponse signature(){
        String sign = vodService.signature();
        return ApiResponse.ofSuccess("success",sign);
    }

    @PostMapping("/video/info/save")
    public ApiResponse save(@RequestBody VodSaveRequest vodSaveRequest, HttpServletRequest request){
        Integer uid = JwtUtils.getId(request);
        Video video = new Video();
        video.setFileId(vodSaveRequest.getFileId());
        video.setCoverUrl(vodSaveRequest.getCoverUrl());
        video.setVideoUrl(vodSaveRequest.getVideoUrl());
        video.setTitle(vodSaveRequest.getName());
        video.setUserId(uid);
        videoService.save(video);
        return ApiResponse.ofSuccess();
    }

    @GetMapping("/video/info")
    public ApiResponse info(Integer vid){
        Video video = videoService.getById(vid);
        return ApiResponse.ofSuccess(packageResponse(video));
    }

    /**
     * 获取当前用户视频列表
     */
    @GetMapping("/video/list")
    public ApiResponse list(HttpServletRequest request
            , @RequestParam(required = false, defaultValue = "10") Integer limit
            , @RequestParam(required = false, defaultValue = "1") Integer page) {
        Integer uid = JwtUtils.getId(request);
        Page<Video> videoPage = videoService.page(new Page<>(page, limit),
                new QueryWrapper<Video>().eq("user_id",uid).orderByDesc("id").eq("status", Constants.DisabledStatus.YES.getCode()));
        return ApiResponse.ofSuccess(videoPage);
    }

    /**
     * 获取正常显示视频列表
     */
    @GetMapping("/video/list/normal")
    public ApiResponse listNormal(@RequestParam(required = false, defaultValue = "10") Integer limit
            , @RequestParam(required = false, defaultValue = "1") Integer page) {
        Page<Video> videoPage = videoService.page(new Page<>(page, limit),
                new QueryWrapper<Video>().orderByDesc("id").eq("status", Constants.DisabledStatus.YES.getCode()));
        Map<String,Object> ret = new HashMap<>(2);
        ret.put("records",videoPage.getRecords().stream().map(this::packageResponse).collect(Collectors.toList()));
        ret.put("total",videoPage.getTotal());
        return ApiResponse.ofSuccess(ret);
    }
    /**
     * 上传成功回调
     *
     */
    @PostMapping("/callback")
    public ApiResponse callback(FileUploadTask request){
        log.info(request.getMediaBasicInfo().getName());
        return ApiResponse.ofSuccess();
    }

    /*
     * {
     *     "EventType":"NewFileUpload",
     *     "FileUploadEvent":{
     *         "FileId":"5285890784273533167",
     *         "MediaBasicInfo":{
     *             "Name":"动物世界",
     *             "Description":"",
     *             "CreateTime":"2019-01-09T16:36:22Z",
     *             "UpdateTime":"2019-01-09T16:36:24Z",
     *             "ExpireTime":"9999-12-31T23:59:59Z",
     *             "ClassId":0,
     *             "ClassName":"其他",
     *             "ClassPath":"其他",
     *             "CoverUrl":"",
     *             "Type":"mp4",
     *             "MediaUrl":"http://125676836723.vod2.myqcloud.com/xxx/xxx/q1BORBPQH1IA.mp4",
     *             "TagSet":[
     *
     *             ],
     *             "StorageRegion":"ap-guangzhou-2",
     *             "SourceInfo":{
     *                 "SourceType":"Upload",
     *                 "SourceContext":""
     *             },
     *             "Vid":"5285890784273533167"
     *         },
     *         "ProcedureTaskId":""
     *     }
     * }
     */

    private VideoResponse packageResponse(Video video){
        User user = userService.getById(video.getUserId());
        VideoResponse response = new VideoResponse();
        response.setId(video.getId());
        response.setTitle(video.getTitle());
        response.setCoverUrl(video.getCoverUrl());
        response.setVideoUrl(video.getVideoUrl());
        if (user != null) {
            response.setUserInfo(new VideoResponse.UserInfo(user.getId(), user.getNickName(), user.getAvatar()));
        }
        return response;
    }
}
