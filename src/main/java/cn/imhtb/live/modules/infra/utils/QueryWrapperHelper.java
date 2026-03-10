package cn.imhtb.live.modules.infra.utils;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.imhtb.live.modules.infra.annotation.QueryFiled;
import cn.imhtb.live.modules.infra.annotation.QueryType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

/**
 * @author pinteh
 * @date 2025/4/13
 */
public class QueryWrapperHelper {

    public static <Q, T> QueryWrapper<T> getQueryWrapper(Q query, List<Field> fields, QueryWrapper<T> queryWrapper) {
        if (query == null) {
            return queryWrapper;
        }
        // 获取D对象的字段列表
        for (Field field : fields) {
            Consumer<QueryWrapper<T>> consumer = getQueryWrapperConsumer(query, field);
            queryWrapper.and(Objects.nonNull(consumer), consumer);
        }
        return queryWrapper;
    }

    private static <Q, T> Consumer<QueryWrapper<T>> getQueryWrapperConsumer(Q query, Field field) {
        Object fieldValue = ReflectUtil.getFieldValue(query, field);
        if (fieldValue == null) {
            return null;
        }
        String fieldName = ReflectUtil.getFieldName(field);
        String underlineCaseFieldName = CharSequenceUtil.toUnderlineCase(fieldName);

        QueryFiled annotation = AnnotationUtil.getAnnotation(field, QueryFiled.class);
        if (annotation == null) {
            // 默认使用等值查询
            return queryWrapper -> queryWrapper.eq(underlineCaseFieldName, fieldValue);
        }

        QueryType type = annotation.type();
        switch (type) {
            case LIKE:
                return queryWrapper -> queryWrapper.like(underlineCaseFieldName, fieldValue);
            case GE:
                return queryWrapper -> queryWrapper.ge(underlineCaseFieldName, fieldValue);
            case LE:
                return queryWrapper -> queryWrapper.le(underlineCaseFieldName, fieldValue);
            case IN: {
                Collection<Object> params = ArrayUtil.isArray(fieldValue) ? Collections.singletonList(fieldValue) : (Collection<Object>) fieldValue;
                return queryWrapper -> queryWrapper.in(underlineCaseFieldName, params);
            }
            case NOT_IN:
                Collection<Object> params = ArrayUtil.isArray(fieldValue) ? Collections.singletonList(fieldValue) : (Collection<Object>) fieldValue;
                return queryWrapper -> queryWrapper.notIn(underlineCaseFieldName, params);
            case IS_NULL:
                return queryWrapper -> queryWrapper.isNull(underlineCaseFieldName);
        }
        return queryWrapper -> queryWrapper.eq(underlineCaseFieldName, fieldValue);
    }

}
