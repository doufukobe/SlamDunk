package com.fpd.basecore.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by t450s on 2016/6/10.
 */
public class StyleCheckUtil {

    public static final int USERNAME_MIN_LENGTH = 6;
    public static final int USERNAME_MAX_LENGTH = 20;
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 16;

    public final static String LOGIN_REGEX = "^(1[0-9]{10})$|^([\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]+)$|^([a-zA-Z0-9_]{6,20})$";

    public static int checkPassword(String string) {

        char[] charStr = string.toCharArray();
        // 65-90||97-122,数字范围是48-57,字符 33-47 58-64 91-96 123-126
        // 33 !
        // 34" 35# 36$ 37% 38& 39' 40( 41) 42* 43+ 44, 45- 46. 47/
        // 58: 59; 60< 61= 62> 63? 64@
        // 91[ 92\ 93] 94^ 95_ 96`
        // 123{ 124| 125} 126~

        int nc = 0;// 数字个数统计
        int cc = 0;// 字母个数统计
        int sc = 0;// 特殊字符个数统计
        int oc = 0;// 其他字符个数统计
        int kc = 0;// 数字、字母和特殊字符种类统计
        for (int i = 0, len = charStr.length; i < len; i++) {
            if (charStr[i] <= 57 && 48 <= charStr[i]) {
                nc++;
            } else if (charStr[i] <= 90 && 65 <= charStr[i] || charStr[i] <= 122 && 97 <= charStr[i]) {
                cc++;
            } else if (charStr[i] <= 47 && 33 <= charStr[i] || charStr[i] <= 64 && 58 <= charStr[i] || charStr[i] <= 96 && 91 <= charStr[i] || charStr[i] <= 126 && 123 <= charStr[i]) {
                sc++;
            } else {
                oc++;
            }
        }

        if (nc > 0) {
            kc++;
        }
        if (cc > 0) {
            kc++;
        }
        if (sc > 0) {
            kc++;
        }

        if (oc > 0) {
            return -1;// 不符合密码要求，输入了除字母、数字和特殊字符外的字符
        } else if (kc >= 2) {
            return 1;// 符合要求
        } else {
            return 0;// 符合密码的要求，但种类不够
        }
    }
    /**
     * count(返回字符串的长度；统计方法，一个汉字或中文字符计2，一个字母或英语标点符号计1)
     */
    public static int count(String str) {
        int singelC = 0, doubleC = 0;
        try {
            String s = "[^\\x00-\\xff]";
            Pattern pattern = Pattern.compile(s);
            Matcher ma = pattern.matcher(str);
            while (ma.find()) {
                doubleC++;
            }
            singelC = str.length() - doubleC;
            doubleC = doubleC * 2 + singelC;
        } catch (Exception e) {
            e.printStackTrace();
            doubleC = 0;
        }

        return doubleC;
    }

    public static boolean checkStringStyle(String str, String style) {
        Pattern REGEX = Pattern.compile(style);
        // 非空判断
        if (null == str) {
            return false;
        }
        Matcher matcher = REGEX.matcher(str);
        return matcher.matches();
    }
}
