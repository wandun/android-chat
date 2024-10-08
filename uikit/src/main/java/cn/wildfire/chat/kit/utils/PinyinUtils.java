/*
 * Copyright (c) 2020 WildFireChat. All rights reserved.
 */

package cn.wildfire.chat.kit.utils;


import android.text.TextUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class PinyinUtils {

    /**
     * 根据传入的字符串(包含汉字),得到拼音
     * 沧晓 -> CANGXIAO
     * 沧  晓*& -> CANGXIAO
     * 沧晓f5 -> CANGXIAO
     *
     * @param str 字符串
     * @return
     */
    public static String getPinyin(String str) {

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

        StringBuilder sb = new StringBuilder();

        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            // 如果是空格, 跳过
            if (Character.isWhitespace(c)) {
                continue;
            }
            if (c >= -127 && c < 128 || !(c >= 0x4E00 && c <= 0x9FA5)) {
                // 肯定不是汉字
                sb.append(c);
            } else {
                String s = "#";
                try {
                    // 通过char得到拼音集合. 单 -> dan, shan
                     String[] pyArr = PinyinHelper.toHanyuPinyinStringArray(c, format);
                     if(pyArr != null && pyArr.length > 0){
                         s = pyArr[0];
                     }
                    sb.append(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        String pinyin = sb.toString();
        if (TextUtils.isEmpty(pinyin)) {
            pinyin = "#";
        }
        return pinyin;
    }

}
