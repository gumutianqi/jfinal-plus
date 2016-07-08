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
package com.beetl.format;

import com.jfinal.kit.PathKit;
import org.apache.commons.lang3.StringEscapeUtils;
import org.beetl.core.Format;
import org.owasp.validator.html.*;

/**
 * 自定义的 Beetl 防止XSS注入函数
 * 同时具备escape转义函数
 * 使用方式 ${data, xss} 、${data, xss="escape"}
 */
public class XSSDefenseFormat implements Format {

    public Object format(Object data, String pattern) {
        if (null == data) {
            return null;
        } else {
            try {
                Policy policy = Policy.getInstance(PathKit.getRootClassPath() + "/antisamy.xml");
                AntiSamy as = new AntiSamy(policy);

                String content = (String) data;
                if ("escape".equals(pattern)) {
                    content = StringEscapeUtils.escapeHtml4(content);
                }

                // clean content
                CleanResults cr = as.scan(content);
                content = cr.getCleanHTML();

                return content;
            } catch (ScanException e) {
                return "xss error.";
            } catch (PolicyException e) {
                return "xss error.";
            }
        }
    }
}
