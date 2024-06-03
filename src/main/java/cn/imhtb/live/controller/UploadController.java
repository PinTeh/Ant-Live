package cn.imhtb.live.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.modules.user.service.IUserService;
import cn.imhtb.live.service.IFileUploadService;
import cn.imhtb.live.service.IRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author PinTeh
 * @date 2020/4/15
 */
@Api(tags = "资源上传接口")
@Slf4j
@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UploadController {

    private final IFileUploadService fileUploadService;
    private final IUserService userService;
    private final IRoomService roomService;

    @PostMapping("/file")
    public ApiResponse<String> uploadFile(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        long now = System.currentTimeMillis() / 1000;
        String newFilename = String.format("%s_%s.%s", FileNameUtils.getBaseName(originalFilename)
                , now
                , FileNameUtils.getExtension(originalFilename));
        try {
            String fileUrl = fileUploadService.uploadFileToMinio(file.getInputStream(), newFilename);
            return ApiResponse.ofSuccess(fileUrl);
        } catch (IOException e) {
            log.error("upload file to minio error", e);
        }
        return ApiResponse.ofError();
    }

    @ApiOperation("上传头像")
    @PostMapping("/avatar")
    public ApiResponse<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        ApiResponse<String> apiResponse = this.uploadFile(file);
        if (apiResponse.isSuccess()) {
            String avatarUrl = apiResponse.getData();
            userService.updateAvatar(avatarUrl);
        }
        return apiResponse;
    }

    @ApiOperation("上传直播间封面")
    @PostMapping("/room/cover")
    public ApiResponse<String> uploadRoomCover(@RequestParam("file") MultipartFile file) {
        ApiResponse<String> apiResponse = this.uploadFile(file);
        if (apiResponse.isSuccess()) {
            String coverUrl = apiResponse.getData();
            roomService.updateCover(coverUrl);
        }
        return apiResponse;
    }

    @RequestMapping()
    public ApiResponse<?> upload(MultipartFile file, String tag) {

        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return ApiResponse.ofError();
        }

        String ext = fileName.substring(fileName.lastIndexOf("."));
        final File excelFile;
        try {
            excelFile = File.createTempFile("temp-" + System.currentTimeMillis(), ext);
            file.transferTo(excelFile);
            String ret = fileUploadService.upload(excelFile);
            deleteFile(excelFile);
            return ApiResponse.ofSuccess(tag, ret);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("File upload fail");
        }
        return ApiResponse.ofError();
    }

    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                boolean ret = file.delete();
                log.info("delete temp file success");
            }
        }
    }
}
