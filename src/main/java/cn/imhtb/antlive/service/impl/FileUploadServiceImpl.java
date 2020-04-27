package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.config.TencentConfig;
import cn.imhtb.antlive.service.IFileUploadService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author PinTeh
 * @date 2020/4/15
 */
@Service
public class FileUploadServiceImpl implements IFileUploadService {

    @Value("${tencent.cos.path}")
    public String path;

    @Value("${tencent.cos.bucketName}")
    public String bucketName;

    private final COSClient client;

    public FileUploadServiceImpl(COSClient client) {
        this.client = client;
    }

    @Override
    public String upload(File file) {

        PutObjectResult putObjectResult = client.putObject(bucketName, file.getName(), file);
        System.out.println(putObjectResult.getETag());
        return path + file.getName();
    }
}
