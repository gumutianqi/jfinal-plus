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

import com.jfinal.log.Log;
import org.slf4j.LoggerFactory;

/**
 * Slf4jLogger 重构
 */
public class Slf4jLogger extends Log {

    private org.slf4j.Logger log;

    Slf4jLogger(Class<?> clazz) {
        log = LoggerFactory.getLogger(clazz);
    }

    Slf4jLogger(String name) {
        log = LoggerFactory.getLogger(name);
    }

    //(marker, this, level, msg,params, t);注意参数顺序
    public void info(String message) {
        log.info(message);
    }

    public void info(String message, Throwable t) {
        log.info(message, t);
    }

    public void debug(String message) {
        log.debug(message);
    }

    public void debug(String message, Throwable t) {
        log.debug(message, t);
    }

    public void warn(String message) {
        log.warn(message);
    }

    public void warn(String message, Throwable t) {
        log.warn(message, t);
    }

    public void error(String message) {
        log.error(message);
    }

    public void error(String message, Throwable t) {
        log.error(message, t);
    }

    public void fatal(String message) {
        log.error(message);
    }

    public void fatal(String message, Throwable t) {
        log.error(message, t);
    }

    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    public boolean isFatalEnabled() {
        return log.isErrorEnabled();
    }
}
