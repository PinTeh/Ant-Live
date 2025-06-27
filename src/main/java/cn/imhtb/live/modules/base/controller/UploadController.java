package cn.imhtb.live.modules.base.controller;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.IdUtil;
import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.modules.user.service.IUserService;
import cn.imhtb.live.service.IFileUploadService;
import cn.imhtb.live.service.IRoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

    private final IUserService userService;
    private final IRoomService roomService;
    private final FileStorageService fileStorageService;
    private final IFileUploadService fileUploadService;

    @ApiOperation("上传文件")
    @PostMapping("/file")
    public ApiResponse<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImgUtil.scale(file.getInputStream(), outputStream, calculateScaleFactor(file.getSize(), 2 * 1024 * 1024));
            String originalFilename = file.getOriginalFilename();
            long now = System.currentTimeMillis() / 1000;
            String newFilename = String.format("%s_%s.%s", IdUtil.simpleUUID(), now
                    , FileNameUtils.getExtension(originalFilename));

            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            String fileUrl = fileUploadService.uploadFileToMinio(inputStream, newFilename);
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

    public ApiResponse<String> compressAndUploadFile(MultipartFile file) {
        FileInfo fileInfo = fileStorageService.of(file)
                .image(img -> img.size(1000, 1000))
                .thumbnail(th -> th.size(200, 200))
                .upload();

        String url = fileInfo.getUrl();
        return ApiResponse.ofSuccess(url);
    }

    public static float calculateScaleFactor(long originalSize, long targetSize) {
        if (targetSize <= 0 || originalSize <= 0) {
            throw new IllegalArgumentException("文件大小必须为正数");
        }
        // 如果目标大小大于原始大小，直接返回1.0（不缩放）
        if (targetSize >= originalSize) {
            return 1.0f;
        }
        // 计算缩放因子：缩放因子 = √(目标大小 / 原始大小)
        double scaleFactor = Math.sqrt((double) targetSize / originalSize);
        // 限制缩放因子最小值，避免过度缩放导致图片模糊
        return (float) Math.max(0.1, scaleFactor); // 最小缩放因子为0.1
    }

}
