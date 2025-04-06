package cn.imhtb.live.service.impl;

import cn.imhtb.live.service.IFileUploadService;
import cn.imhtb.live.common.utils.MinioUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

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

    @Autowired(required = false)
    private COSClient client;

    @Override
    public String upload(File file) {
        PutObjectResult putObjectResult = client.putObject(bucketName, file.getName(), file);
        System.out.println(putObjectResult.getETag());
        return path + file.getName();
    }

    @Override
    public String uploadFileToMinio(InputStream inputStream, String newFilename) {
        return MinioUtil.uploadObjectWithInputStream(newFilename, inputStream);
    }

}
