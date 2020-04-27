package cn.imhtb.antlive.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @author PinTeh
 */
@Configuration
public class MyMeteObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasSetter("createTime")&&getFieldValByName("createTime",metaObject)==null) {
            this.strictInsertFill(metaObject,"createTime",LocalDateTime.class,LocalDateTime.now());
        }
        if (metaObject.hasSetter("updateTime")&&getFieldValByName("updateTime",metaObject)==null) {
            this.strictUpdateFill(metaObject,"updateTime",LocalDateTime.class,LocalDateTime.now());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasSetter("updateTime")&&getFieldValByName("updateTime",metaObject)==null) {
            this.strictUpdateFill(metaObject,"updateTime",LocalDateTime.class,LocalDateTime.now());
        }
    }

}
