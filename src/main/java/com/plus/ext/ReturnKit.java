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
package com.plus.ext;

import com.jfinal.core.Controller;
import com.jfinal.render.JsonRender;
import com.jfinal.render.Render;

import javax.servlet.http.HttpServletRequest;

/**
 * ReturnKit
 */
public class ReturnKit {
    private static String dataTypeName = "returnType";

    public static String getDataTypeName() {
        return dataTypeName;
    }

    public static void setDataTypeName(String dataTypeＮame) {
        ReturnKit.dataTypeName = dataTypeＮame;
    }


    public static boolean isAjax(HttpServletRequest request) {
        return ("XMLHttpRequest").equalsIgnoreCase(request.getHeader("X-Requested-With"));// 如果是ajax请求响应头会有，x-requested-with；
    }

    public static boolean isJson(HttpServletRequest request, String dataTypeName) {
        return (isAjax(request) && !("default").equalsIgnoreCase(request.getParameter(dataTypeName))) ||
                ("json").equalsIgnoreCase(request.getParameter(dataTypeName));// 如果是ajax请求响应头会有，x-requested-with；
    }

    public static boolean isJson(HttpServletRequest request) {
        return isJson(request, dataTypeName);
    }

    public static boolean isJson(Controller controller) {
        return controller.getRender() instanceof JsonRender;
    }

    public static boolean isJson(Render render) {
        return render instanceof JsonRender;
    }


    public enum ReturnType {
        DEFAULT(0), JSON(1);
        private final int value;

        private ReturnType(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }
}
