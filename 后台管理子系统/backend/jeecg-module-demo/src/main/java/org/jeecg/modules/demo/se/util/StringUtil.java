package org.jeecg.modules.demo.se.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Custom
 * @date 2024/5/8
 */
public class StringUtil {
    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /** 下划线转驼峰 */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String lineToHumpClassName(String str) {
        String s = lineToHump(str);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
