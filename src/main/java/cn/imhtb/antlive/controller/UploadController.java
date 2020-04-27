package cn.imhtb.antlive.controller;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.service.IFileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

    private final IFileUploadService fileUploadService;

    public UploadController(IFileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @RequestMapping()
    public ApiResponse upload(MultipartFile file,String tag){

        String fileName = file.getOriginalFilename();
        if (fileName==null){
            return ApiResponse.ofError();
        }

        String ext = fileName.substring(fileName.lastIndexOf("."));
        final File excelFile;
        try {
            excelFile = File.createTempFile("temp-"+System.currentTimeMillis(), ext);
            file.transferTo(excelFile);
            String ret = fileUploadService.upload(excelFile);
            deleteFile(excelFile);
            return ApiResponse.ofSuccess(tag,ret);
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
