package cn.imhtb.live;

import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author pinteh
 */
@MapperScan("cn.imhtb.live.mappers")
@EnableCaching
@EnableScheduling
@EnableFileStorage
@SpringBootApplication
public class AntLiveApplication{

    public static void main(String[] args) {
        SpringApplication.run(AntLiveApplication.class, args);
    }

}
