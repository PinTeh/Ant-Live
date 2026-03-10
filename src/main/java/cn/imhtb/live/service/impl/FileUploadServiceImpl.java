package cn.imhtb.live.service.impl;

import cn.imhtb.live.common.utils.MinioUtil;
import cn.imhtb.live.service.IFileUploadService;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author PinTeh
 * @date 2020/4/15
 */
@Service
public class FileUploadServiceImpl implements IFileUploadService {

    @Override
    public String uploadFileToMinio(InputStream inputStream, String newFilename) {
        return MinioUtil.uploadObjectWithInputStream(newFilename, inputStream);
    }

}
