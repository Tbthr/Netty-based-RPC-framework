package com.lyq.utils;

import java.util.Arrays;

/**
 * String 工具类
 */
public class StringUtil {

    public static boolean isBlank(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        for (int i = 0; i < s.length(); ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String join(Object[] objects) {
        if (objects == null || objects.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Arrays.stream(objects).forEach(sb::append);
        return sb.toString();
    }
}
