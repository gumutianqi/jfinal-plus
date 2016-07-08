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
package com.plus.ext.handler;

import com.jfinal.handler.Handler;
import com.jfinal.render.RenderFactory;
import com.plus.ext.util.matcher.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拒绝访问的URL
 */
public class AccessDeniedHandler extends Handler {
    /**
     * 拒绝访问的URL
     */
    private String[] accessDeniedUrls;

    AntPathMatcher antMatcher = new AntPathMatcher();

    public AccessDeniedHandler(String... accessDeniedUrls) {
        this.accessDeniedUrls = accessDeniedUrls;
    }

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (checkView(target)) {
            isHandled[0] = true;
            RenderFactory.me().getErrorRender(403).setContext(request, response).render();
            return;
        }
        next.handle(target, request, response, isHandled);
    }

    public boolean checkView(String viewUrl) {

        if (accessDeniedUrls != null && accessDeniedUrls.length > 0) {
            for (String url : accessDeniedUrls) {
                if (antMatcher.match(url, viewUrl)) {
                    return true;
                }
            }
        }
        return false;
    }
}
