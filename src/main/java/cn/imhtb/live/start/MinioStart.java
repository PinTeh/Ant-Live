package cn.imhtb.live.start;

import cn.imhtb.live.common.config.MinioConfig;
import cn.imhtb.live.common.utils.MinioUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

/**
 * @author pinteh
 * @date 2023/5/13
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MinioStart implements CommandLineRunner {

    private final MinioConfig minioConfig;

    @SuppressWarnings("all")
    @Override
    public void run(String... args) throws Exception {
        Executors.newSingleThreadExecutor().execute(()->{
             MinioUtil.init();
             log.info("minio init finished, config = {}", minioConfig);
        });
    }

}
