package cn.imhtb.live.service;

import java.io.File;
import java.io.InputStream;

/**
 * @author PinTeh
 * @date 2020/4/15
 */
public interface IFileUploadService {

    /**
     * 上传文件
     *
     * @param file 文件
     * @return {@link String}
     */
    String upload(File file);


    /**
     * 将文件上传到minio
     *
     * @param inputStream 输入流
     * @param newFilename 新文件名
     * @return {@link String}
     */
    String uploadFileToMinio(InputStream inputStream, String newFilename);

}
