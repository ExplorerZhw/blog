package com.zhw.blog.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahoi on 2018-1-26 11:32:47
 * 集合工具类
 */
public class ListUtils {

    public static boolean isNotEmpty(List list) {
        return list != null && list.size() > 0;
    }

    public static boolean isEmpty(List list) {
        return !isNotEmpty(list);
    }

    /**
     * 返回指定长度的集合
     */
    public static List getEmptyList(int length) {
        List list = new ArrayList();
        for (int i = 0; i < length; i++) {
            list.add(0);
        }
        return list;
    }
}
