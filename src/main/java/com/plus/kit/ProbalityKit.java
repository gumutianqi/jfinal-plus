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

import java.util.Map;
import java.util.Random;

/**
 * 概率计算
 */
public class ProbalityKit {
    /**
     * 概率选择
     *
     * @param keyChanceMap key为唯一标识，value为该标识的概率，是去掉%的数字
     * @return 被选中的key。未选中返回null
     */
    public static String chanceRate(Map<String, Integer> keyChanceMap) {
        if (keyChanceMap == null || keyChanceMap.size() == 0)
            return null;

        Integer sum = 0;
        for (Integer value : keyChanceMap.values()) {
            sum += value;
        }
        // 从1开始
        Integer rand = new Random().nextInt(sum) + 1;

        for (Map.Entry<String, Integer> entry : keyChanceMap.entrySet()) {
            rand -= entry.getValue();
            // 选中
            if (rand <= 0) {
                return entry.getKey();
            }
        }
        return null;
    }
}
