package com.zhw.blog.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class UUIDUtil {

    private static  String uuidStr;

    public static String getUuidStr() {
        createUuid();
        return uuidStr;
    }

    private static String createUuid() {
        UUID uuid = UUID.randomUUID();
        if (uuid == null || StringUtils.isEmpty(uuid.toString())) {
            uuidStr = createUuid();
        } else {
            uuidStr = uuid.toString();
        }
        return uuidStr;
    }
}
