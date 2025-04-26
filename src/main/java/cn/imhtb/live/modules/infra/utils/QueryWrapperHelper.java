package cn.imhtb.live.modules.infra.utils;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author pinteh
 * @date 2025/4/13
 */
public class QueryWrapperHelper {

    public static <Q, T> QueryWrapper<T> getQueryWrapper(Q query, List<Field> fields, QueryWrapper<T> queryWrapper){
        if (query == null){
            return queryWrapper;
        }
        // 获取D对象的字段列表
        for (Field field : fields) {
            Consumer<QueryWrapper<T>> consumer = getQueryWrapperConsumer(query, field);
            queryWrapper.and(Objects.nonNull(consumer), consumer);
        }
        return queryWrapper;
    }

    private static <Q, T> Consumer<QueryWrapper<T>> getQueryWrapperConsumer(Q query, Field field){
        Object fieldValue = ReflectUtil.getFieldValue(query, field);
        if (fieldValue == null){
            return null;
        }
        String fieldName = ReflectUtil.getFieldName(field);
        // TODO 默认使用等值查询 AnnotationUtil.getAnnotation(field, Annotation.class);
        return queryWrapper -> queryWrapper.eq(CharSequenceUtil.toUnderlineCase(fieldName), fieldValue);
    }

}
