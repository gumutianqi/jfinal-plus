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

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

/**
 * 随机数生成工具类
 */
public class RandomKit {
    /**
     * 26个小写字母加数字
     */
    private static final char[] LETTER_NUMBER = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9'};

    /**
     * 26个大小写字母加数字
     */
    private static final char[] LETTER_NUMBER_CASE = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9'};

    /**
     * 26个小写字母
     */
    private static final char[] LETTER = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * 26个大小写字母加数字
     */
    private static final char[] LETTER_CASE = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 10个数字
     */
    private static final char[] NUMBER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 按指定大小在<b>26个英文字母</b>中生成随机数。
     *
     * @param t 生成的长度，t不能小于1或大于256，否则返回"0"
     * @param b 是否排除混淆il10
     * @return 你想要的随机数
     * @created 2013-5-16 下午02:40:05
     * @author Belen
     */
    public static String getRandomOfLetter(Integer t, Boolean b) {
        return get(LETTER, t, b);
    }

    /**
     * 按指定大小在<b>26个英文字母</b>中生成随机数。
     *
     * @param t 生成的长度，t不能小于1或大于256，否则返回"0"
     * @param b 是否排除混淆il10
     * @return 你想要的随机数
     * @created 2013-5-16 下午02:40:05
     * @author Belen
     */
    public static String getRandomOfLetterCase(Integer t, Boolean b) {
        return get(LETTER_CASE, t, b);
    }

    /**
     * 按指定大小在<b>0-9</b>数字中生成随机数。
     *
     * @param t 生成的长度，t不能小于1或大于256，否则返回"0"
     * @param b 是否排除混淆il10
     * @return 你想要的随机数
     * @created 2013-5-16 下午02:40:05
     * @author Belen
     */
    public static String getRandomOfNumber(Integer t, Boolean b) {
        return get(NUMBER, t, b);
    }

    /**
     * 按指定大小在<b>25个英文以及10个数字</b>中生成随机数。
     *
     * @param t 生成的长度，t不能小于1或大于256，否则返回"0"
     * @param b 是否排除混淆il10
     * @return 你想要的随机数
     * @created 2013-5-16 下午02:40:05
     * @author Belen
     */
    public static String getRandomOfLetterAndNumber(Integer t, Boolean b) {
        return get(LETTER_NUMBER, t, b);
    }

    /**
     * 按指定大小在<b>25个大小写英文以及10个数字</b>中生成随机数。
     *
     * @param t 生成的长度，t不能小于1或大于256，否则返回"0"
     * @param b 是否排除混淆il10
     * @return 你想要的随机数
     */
    public static String getRandomOfLetterAndNumberCase(Integer t, Boolean b) {
        String str = get(LETTER_NUMBER_CASE, t, b);
        Random random = new Random();
        StringBuffer sb = new StringBuffer("");
        for (char c : str.toCharArray()) {
            String result = String.valueOf(c);
            int i = Math.abs(random.nextInt(64));
            if (i > 20 && i < 40) {
                int num = Math.abs(random.nextInt(9));
                if (!b && num != 0 && num != 1) {
                    result = num + "";
                }
            }
            sb.append(result);
        }
        return sb.toString();
    }

    /**
     * 生成指定范围的随机数
     *
     * @param min 最小结果
     * @param max 最大结果
     * @return
     */
    public static Integer getRandomNum(Integer min, Integer max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 按指定数组生成数据。
     *
     * @param c    字典
     * @param t    长度
     * @param bool 是否排除容易混淆的自负[ilo01IOQ]
     * @return
     */
    private static String get(char[] c, Integer t, boolean bool) {
        if (t < 1 || t > 256) {
            return "0";
        }

        final int maxNum = 64;
        int i; // 生成的随机数
        int count = 0; // 生成的长度

        StringBuffer sb = new StringBuffer("");
        Random r = new Random();
        while (count < t) {
            // 生成随机数，取绝对值，防止生成负数，
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为64-1
            if (i >= 0 && i < c.length) {
                if (bool && StringUtils.contains("ilo01IOQ", String.valueOf(c[i]))) {
                    continue;
                } else {
                    sb.append(c[i]);
                }
                count++;
            }
        }
        return sb.toString();
    }
}
