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
package com.plus.ext.handler.xss;

import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP 请求过滤
 */
public class AttackHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public AttackHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 重写并过滤getParameter方法
     *
     * @param name name
     * @return param
     */
    @Override
    public String getParameter(String name) {
        return escapeAll(super.getParameter(name));
    }

    /**
     * 重写并过滤getParameterValues方法
     *
     * @param name name
     * @return value
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (null == values) {
            return null;
        }
        for (int i = 0; i < values.length; i++) {

            values[i] = escapeAll(values[i]);
        }
        return values;
    }

    /**
     * 重写并过滤getParameterMap方法
     *
     * @return parammap
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> paramsMap = super.getParameterMap();
        // 对于paramsMap为空的直接return
        if (null == paramsMap || paramsMap.isEmpty()) {
            return paramsMap;
        }

        HashMap newParamsMap = new HashMap(paramsMap);
        for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            if (null == values) {
                continue;
            }
            String[] newValues = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                newValues[i] = escapeAll(values[i]);
            }
            newParamsMap.put(key, values);
        }
        return Collections.unmodifiableMap(newParamsMap);
    }

    public String escapeAll(String text) {

        String value = text;
        if (text == null) {
            return text;
        } else {
            value = escapeString(value);
        }
        return value;
    }

    public String escapeString(String text) {

        String value = text;
        if (text == null) {
            return text;
        } else {
            value = escapeHtml(value);
            value = escapeScript(value);
        }
        return value;
    }


    public String escapeHtml(String text) {

        String value = text;
        if (text == null) {
            return text;
        } else {
            value = StringEscapeUtils.escapeHtml3(value);
            value = StringEscapeUtils.escapeHtml4(value);
        }
        return value;
    }

    public String escapeScript(String text) {

        String value = text;
        if (text == null) {
            return text;
        } else {
            value = StringEscapeUtils.escapeEcmaScript(value);
        }
        return value;
    }
}
