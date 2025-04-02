package cn.imhtb.live.utils;

import cn.imhtb.live.config.MinioConfig;
import io.minio.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * @author pinteh
 * @date 2023/5/13
 */
@Slf4j
public class MinioUtil {

    private static MinioConfig minioConfig;

    private static MinioClient minioClient;

    private static String DEFAULT_BUCKET;

    private static String ENDPOINT;

    public static synchronized void init() {
        if (minioClient != null) {
            return;
        }
        try {
            minioConfig = SpringContextUtil.getBean(MinioConfig.class);
            // init client
            String readOnlyPolicyArgs = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:GetObject\"],\"Resource\":[\"arn:aws:s3:::%s/*\"]}]}\n";

            minioClient = MinioClient.builder()
                    .endpoint(minioConfig.getIp(), minioConfig.getPort(), false)
                    .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                    .build();

            DEFAULT_BUCKET = minioConfig.getBucketName();
            ENDPOINT = minioConfig.getEndpoint();
            createBucket(DEFAULT_BUCKET);

            // init bucket policy
            minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                    .bucket(DEFAULT_BUCKET)
                    .config(String.format(readOnlyPolicyArgs, DEFAULT_BUCKET))
                    .build());
        } catch (Exception e) {
            log.error("init minio error", e);
        }
    }

    @SneakyThrows(Exception.class)
    public static void uploadObject(String object, String fileName) {
        minioClient.uploadObject(UploadObjectArgs.builder()
                .bucket(DEFAULT_BUCKET)
                .filename(fileName)
                .object(object)
                .build());
    }

    @SneakyThrows(Exception.class)
    public static String uploadObjectWithInputStream(String object, InputStream stream) {
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                .bucket(DEFAULT_BUCKET)
                .object(object)
                .stream(stream, stream.available(), -1)
                .build());
        return getSplicingObjectUrl(DEFAULT_BUCKET, object);
    }

    @SneakyThrows(Exception.class)
    public static void createBucket(String bucket) {
        boolean exists = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucket)
                .build());
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucket)
                    .build());
        }
    }

    @SneakyThrows(Exception.class)
    public static void removeObject(String object) {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(DEFAULT_BUCKET)
                .object(object)
                .build());
    }

    /**
     * 获取拼接对象地址
     *
     * @param object 对象key
     * @return 地址
     */
    public static String getSplicingObjectUrl(String bucket, String object) {
        boolean relative = minioConfig.isRelative();
        if (Boolean.TRUE.equals(relative)){
            return String.format("/%s/%s", bucket, object);
        }
        return String.format("%s/%s/%s", ENDPOINT, bucket, object);
    }

}
