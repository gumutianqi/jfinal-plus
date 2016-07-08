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
package com.plus.ext.plugin.log;

import com.jfinal.log.ILogFactory;
import com.jfinal.log.Log;

/**
 * 用Slf4j替换Log4j进行日志处理
 */
public class Slf4jLogFactory implements ILogFactory {

    @Override
    public Log getLog(Class<?> clazz) {
        return new Slf4jLogger(clazz);
    }

    @Override
    public Log getLog(String name) {
        return new Slf4jLogger(name);
    }
}
