package com.lzl.wiki.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类放在项目包下java下的utils中
 * <h3>wiki</h3>
 * <p>批量处理赋值工具类</p>
 *
 * @author : 黎钟龙
 * QQ:1661548820
 * Mail：1661548820@qq.com
 * @date : 2022-01-09 09:55
 **/
public class CopyUtil {
    /**
     * 单体复制
     */
    public static <T> T copy(Object source,Class<T> clazz){
        if (source==null){
            return null;
        }
        T obj=null;
        try {
//            给目标对象初始化实例
            obj=clazz.newInstance();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        BeanUtils.copyProperties(source,obj);
        return obj;
    }

    /**
     * 集合复制
     */
    public static <T> List<T> copyList(List source,Class<T> clazz){
//        初始换输出的List
        List<T> target=new ArrayList<>();
//        源对象是空则不复制，不为空则需要复制
        if (!CollectionUtils.isEmpty(source)){
//            循环复制多个对象
            for (Object c : source) {
//                调用单个复制方法
                T obj=copy(c,clazz);
//                把复完值的对象放入到list中
                target.add(obj);
            }
        }
        return target;
    }
}
