package cn.imhtb.live.modules.infra.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author pinteh
 * @date 2025/4/29
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryFiled {

    /**
     * 查询方式
     */
    QueryType type() default QueryType.EQ;

}
