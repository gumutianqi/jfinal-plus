/**
 * Copyright (c) 2009-2016, LarryKoo 老古 (gumutianqi@gmail.com)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.plus.kit;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JAVA 正则表达式工具
 */
public class PatternKit {
    /**
     * @param str
     * @return boolean
     * @throws
     * @Title: isContainsChinese
     * @Description: 判断一个字符串是否包含中文
     */
    public static Boolean hasContainsChinese(String str) {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]");
        return isContains(pattern, str);
    }

    public static Boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return isContains(pattern, str);
    }

    /**
     * 判断是否手机号码，适用于中国大陆
     *
     * @param str
     * @return
     */
    public static Boolean isMoblie(String str) {
        Pattern pattern = Pattern.compile("^[1][0-9]{10}$");
        return isContains(pattern, str);
    }

    /**
     * 判断是否车牌号
     *
     * @param str
     * @return
     */
    public static Boolean isPlate(String str) {
        Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5].+$");
        return isContains(pattern, str);
    }

    /**
     * 判断是否车牌号，适用于中国大陆
     *
     * @param str
     * @return
     */
    public static Boolean isCNPlate(String str) {
        Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5][\\da-zA-Z]{6}$");
        return isContains(pattern, str);
    }

    /**
     * @param str
     * @return boolean
     * @throws
     * @Title: hasCrossScriptRisk
     * @Description: 判断一个字符串中是否包含有这些特殊字符
     */
    public static Boolean hasCrossScriptRisk(String str) {
        Pattern pattern = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");
        return isContains(pattern, str);
    }

    /**
     * 判断一个字符串是否为数字开头
     *
     * @param str
     * @return
     */
    public static Boolean isStartWithNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return isContains(pattern, str);
    }

    /**
     * @param min
     * @param max
     * @return int
     * @throws
     * @Title: RandomNum
     * @Description: 产生一个min到max范围内的随机整数
     */
    public static Integer randomNum(Integer min, Integer max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    /**
     * @param pattern 正则对象
     * @param str     需要判断的字符串
     * @return boolean
     * @throws
     * @Title: isContains
     * @Description: 判断str中是否包含pat
     */
    public static Boolean isContains(Pattern pattern, String str) {
        Matcher matcher = pattern.matcher(str);
        boolean flg = false;
        if (matcher.find()) {
            flg = true;
        }
        return flg;
    }
}
