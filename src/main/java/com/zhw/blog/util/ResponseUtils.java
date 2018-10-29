package com.zhw.blog.util;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {
    private static final String KEY_STATUS = "status"; // 1成功；0错误
    private static final String KEY_DATA = "data"; // 1成功；0错误

    public static Map writeSuccess(String data) {
        return writeCommon("1", data);
    }

    public static Map writeError(String data) {
        return writeCommon("0", data);
    }

    public static Map writeCommon(String status, Object data) {
        Map map = new HashMap();
        map.put(KEY_STATUS, status);
        map.put(KEY_DATA, data);
        return map;
    }
}
