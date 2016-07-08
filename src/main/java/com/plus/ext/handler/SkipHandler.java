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
import com.plus.ext.util.matcher.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跳过URL不做处理
 */
public class SkipHandler extends Handler {
    /**
     * 跳过的URL
     */
    private String[] skipUrls;

    AntPathMatcher antMatcher = new AntPathMatcher();

    public SkipHandler(String... skipUrls) {
        this.skipUrls = skipUrls;
    }

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (checkSkip(target)) {
            return;
        }
        next.handle(target, request, response, isHandled);
    }

    public boolean checkSkip(String skipUrl) {
        if (skipUrls != null && skipUrls.length > 0) {
            for (String url : skipUrls) {
                if (antMatcher.match(url, skipUrl)) {
                    return true;
                }
            }
        }
        return false;
    }
}
