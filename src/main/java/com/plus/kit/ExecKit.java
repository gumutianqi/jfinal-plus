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
import org.apache.commons.exec.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * @author LarryKoo(larrykoo@126.com)
 * @date 2015-12-28
 */
@Slf4j
public class ExecKit {
    /**
     * ======================================================
     */

    /**
     * 执行命令返回字符串结果
     *
     * @param command 传入的命令
     * @param timeout 执行超时时间,单位秒
     * @return
     */
    public static ExecModel exec(String command, Long timeout) {
        ExecModel execModel = new ExecModel();
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();

            CommandLine commandLine = CommandLine.parse(command);
            DefaultExecutor exec = new DefaultExecutor();
            exec.setExitValues(null);

            //定时狗监控超时
            ExecuteWatchdog watchdog = new ExecuteWatchdog(1000 * timeout);
            exec.setWatchdog(watchdog);

            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream, errorStream);
            exec.setStreamHandler(streamHandler);
            exec.execute(commandLine);

            execModel.setOutMsg(outputStream.toString("utf-8"));
            execModel.setErrorMsg(errorStream.toString("utf-8"));
        } catch (ExecuteException e) {
            execModel.addException("ExecuteException", e.getMessage());
            throw Exceptions.unchecked(e);
        } catch (IOException e) {
            execModel.addException("IOException", e.getMessage());
            throw Exceptions.unchecked(e);
        } catch (Exception e) {
            execModel.addException("Exception", e.getMessage());
            throw Exceptions.unchecked(e);
        } finally {
            return execModel;
        }
    }


}
