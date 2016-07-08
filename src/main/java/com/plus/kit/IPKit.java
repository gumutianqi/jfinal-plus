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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.HttpKit;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class IPKit {
    public static String api_taobao_ip = "http://ip.taobao.com/service/getIpInfo.php?ip=YOURIP";

    public static String api_baidu_ip = "";

    /**
     * @param request
     * @return String
     * @throws
     * @Title: getIpAddr
     * @Description: 通过HttpServletRequest获取用户真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.trim().length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.trim().length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.trim().length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.trim().length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        ip = ip != null && ip.indexOf(",") > 0 ? ip.split(",")[0] : ip;
        return ip;
    }


    /**
     * @param request
     * @return TaoBaoIP
     * @throws
     * @Title: getTaoBaoIP
     * @Description: 调用淘宝IP地址接口，获取IP地址相信信息
     */
    public static TaobaoIPEntity getTaoBaoIP(HttpServletRequest request) {
        TaobaoIPEntity tbIp = new TaobaoIPEntity();
        String ip = getIpAddr(request);
        if (ip.equals("127.0.0.1")) {
            tbIp.setIp("127.0.0.1");
            tbIp.setCountry("LOCAL");
        } else {
            String url = api_taobao_ip.replace("YOURIP", ip);
            String res = HttpKit.get(url);
            if (null != res) {
                JSONObject json = JSON.parseObject(res);
                if (!json.isEmpty() && json.getInteger("code") == 0) {
                    tbIp = json.getObject("data", TaobaoIPEntity.class);
                }
            }
        }
        return tbIp;
    }

    /**
     * @return String
     * @throws
     * @Title: getIpInfo
     * @Description: 获取IP地址Info信息
     */
    public static String getIpInfo(TaobaoIPEntity tbIp) {
        String info;
        if (null != tbIp) {
            StringBuilder sb = new StringBuilder();
            if (!tbIp.getIp().equals("127.0.0.1")) {
                sb.append(tbIp.getIsp()).append(" ");
                sb.append(tbIp.getCountry()).append(" ");
                sb.append(tbIp.getArea()).append(" ");
                if (!tbIp.getRegion().equals(tbIp.getCity())) {
                    sb.append(tbIp.getRegion()).append(" ");
                }
                sb.append(tbIp.getCity());
            } else {
                sb.append(tbIp.getCountry());
            }
            info = sb.toString();
        } else {
            info = "Not found ip information.";
        }
        return info;
    }

    @Data
    static class TaobaoIPEntity implements Serializable {
        private static final long serialVersionUID = -2657423891949348106L;
        private String country;
        private String country_id;
        private String area;
        private String area_id;
        private String region;
        private String region_id;
        private String city;
        private String city_id;
        private String county;
        private String county_id;
        private String isp;
        private String isp_id;
        private String ip;
    }
}
