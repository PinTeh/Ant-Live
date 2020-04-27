package cn.imhtb.antlive;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.imhtb.antlive.mappers")
public class AntLiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntLiveApplication.class, args);
    }

}
