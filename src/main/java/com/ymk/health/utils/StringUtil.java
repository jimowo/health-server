package com.ymk.health.utils;

/**
 * 字符串判断
 */
public class StringUtil {

    /**
     * 判断字符串不为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !"".equals(str);
    }
}
