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
package com.plus.ext.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.plus.ext.ReturnKit;

import javax.servlet.http.HttpServletRequest;

/**
 * 过滤掉不需要处理的URL
 */
public class URLInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation ai) {
        Controller controller = ai.getController();
        HttpServletRequest request = controller.getRequest();
        //webRoot
        controller.setAttr("_webRootPath", request.getScheme() + "://"
                + request.getServerName() + (request.getServerPort() == 80 ? "" : ":" + request.getServerPort())
                + request.getContextPath());

        ai.invoke();

        if (!ReturnKit.isJson(controller)) {
            //local 数据
            controller.setAttr("_localParas", request.getQueryString());
            controller.setAttr("_localUri", ai.getActionKey());
        }
    }
}