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

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.owasp.validator.html.*;

/**
 * XSS防跨站工具脚本过滤
 */
@Slf4j
public class XSSKit {
    /**
     * 过滤HTML内容
     *
     * @param content  html内容
     * @param rootPath 过滤规则配置文件根路径
     * @return
     */
    public static String clearHTML(String content, String rootPath) {
        try {
            if (StringUtils.isBlank(rootPath)) {
                rootPath = XSSKit.class.getResource("/").getPath();
            }
            Policy policy = Policy.getInstance(rootPath + "/antisamy.xml");
            AntiSamy as = new AntiSamy(policy);

            // clean content
            CleanResults cr = as.scan(content);
            content = cr.getCleanHTML();

            return content;
        } catch (ScanException e) {
            log.error("xss ScanException error.");
            return null;
        } catch (PolicyException e) {
            log.error("xss PolicyException error.");
            return null;
        }
    }
}
