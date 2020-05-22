package cn.imhtb.antlive;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("cn.imhtb.antlive.mappers")
@EnableCaching
public class AntLiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntLiveApplication.class, args);
    }

}
