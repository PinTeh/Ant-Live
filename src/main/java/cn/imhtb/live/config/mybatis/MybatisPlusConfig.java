package cn.imhtb.live.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Mybatis Plus Configuration
 *
 * @author PinTeh
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Component
    static class MyMeteObjectHandler implements MetaObjectHandler {

        @Override
        public void insertFill(MetaObject metaObject) {
            String createTime = "createTime";
            String updateTime = "updateTime";
            if (metaObject.hasSetter(createTime) && getFieldValByName(createTime, metaObject) == null) {
                this.strictInsertFill(metaObject, createTime, LocalDateTime.class, LocalDateTime.now());
            }
            if (metaObject.hasSetter(updateTime) && getFieldValByName(updateTime, metaObject) == null) {
                this.strictUpdateFill(metaObject, updateTime, LocalDateTime.class, LocalDateTime.now());
            }
        }

        @Override
        public void updateFill(MetaObject metaObject) {
            String updateTime = "updateTime";
            if (metaObject.hasSetter(updateTime) && getFieldValByName(updateTime, metaObject) == null) {
                this.strictUpdateFill(metaObject, updateTime, LocalDateTime.class, LocalDateTime.now());
            }
        }
    }

}
