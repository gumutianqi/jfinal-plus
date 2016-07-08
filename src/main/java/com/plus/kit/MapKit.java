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

import java.text.DecimalFormat;

/**
 * 计算经纬度球面距离的工具
 */
public class MapKit {
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(Double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算两个经纬度之间的距离
     *
     * @param lat1 纬度1
     * @param lng1 经度1
     * @param lat2 纬度2
     * @param lng2 经度2
     * @return
     */
    public static Double getDistance(Double lat1, Double lng1, Double lat2, Double lng2) {
        Double radLat1 = rad(lat1);
        Double radLat2 = rad(lat2);
        Double a = radLat1 - radLat2;
        Double b = rad(lng1) - rad(lng2);
        Double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        DecimalFormat df = new DecimalFormat("#.00000");
        s = Double.parseDouble(df.format(s));
        return s;
    }


    /**
     * 对距离进行中文的转换
     *
     * @param s    长度距离km
     * @param unit 单位数组,默认new String[]{"km", "m"}
     * @return
     */
    public static String getMapMI(Double s, String[] unit) {
        String lg;
        unit[0] = StringUtils.isBlank(unit[0]) ? "km" : unit[0];
        unit[1] = StringUtils.isBlank(unit[1]) ? "m" : unit[1];

        if (s >= 1) {
            lg = new DecimalFormat("#.00").format(s) + unit[0];
        } else {
            Double distance = Double.parseDouble(new DecimalFormat("#.00000").format(s)) * 1000;
            distance = Double.parseDouble(new DecimalFormat("#.0").format(distance));
            if (distance > 0 && distance < 1) {
                lg = distance + unit[1];
            } else {
                lg = (int) Math.round(distance) + unit[1];
            }
        }
        return lg;
    }
}
