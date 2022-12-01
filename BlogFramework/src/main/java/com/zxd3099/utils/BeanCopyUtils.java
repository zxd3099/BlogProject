package com.zxd3099.utils;


import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zxd3099
 * @create 2022-11-18-15:24
 */
public class BeanCopyUtils {
    private BeanCopyUtils() {}

    public static <V> V copyBean(Object source, Class<V> clazz) {
        // 创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            // 实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回结果
        return result;
    }

    public static <O, V> List<V> copyBeanList(List<O> list, Class<V> clazz) {
        return list.stream().map(o -> copyBean(o, clazz)).collect(Collectors.toList());
    }
}
