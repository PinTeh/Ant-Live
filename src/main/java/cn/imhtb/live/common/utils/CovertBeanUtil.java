package cn.imhtb.live.common.utils;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pinteh
 * @date 2024/4/26
 */
public class CovertBeanUtil {

    public static <T> T convert(Object source, Class<T> targetClass){
        if (source == null) {
            return null;
        }
        T t = newInstance(targetClass);
        BeanUtils.copyProperties(source, t);
        return t;
    }

    public static <S, T> T convert(S source, Class<T> targetClass, ConvertCallBack<S, T> callBack){
        if (source == null) {
            return null;
        }
        T t = newInstance(targetClass);
        BeanUtils.copyProperties(source, t);
        if (callBack != null){
            callBack.callBack(source, t);
        }
        return t;
    }

    @SuppressWarnings("all")
    public static <S, T> List<T> covertList(List<S> sourceList, Class<T> targetClass){
        if (sourceList == null){
            return null;
        }
        List targetList = new ArrayList<>((int) (sourceList.size() / 0.75) + 1);
        for (S s : sourceList) {
            targetList.add(convert(s, targetClass));
        }
        return targetList;
    }

    @SuppressWarnings("all")
    public static <S, T> List<T> covertList(List<S> sourceList, Class<T> targetClass, ConvertCallBack<S, T> callBack){
        if (sourceList == null){
            return null;
        }
        List targetList = new ArrayList<>((int) (sourceList.size() / 0.75) + 1);
        for (S s : sourceList) {
            T t = convert(s, targetClass);
            targetList.add(t);
            if (callBack != null){
                callBack.callBack(s, t);
            }
        }
        return targetList;
    }

    public static <T> T newInstance(Class<T> targetClass){
        try {
            return targetClass.getDeclaredConstructor().newInstance();
        }catch (Exception e){
            throw new BeanInstantiationException(targetClass, "instance error", e);
        }
    }

    /**
     * 回调接口
     *
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     */
    @FunctionalInterface
    public interface ConvertCallBack<S, T> {

        /**
         * 回调
         *
         * @param t
         * @param s
         */
        void callBack(S t, T s);
    }

}