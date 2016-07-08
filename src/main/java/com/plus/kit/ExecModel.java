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

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * Exec 执行返回结果
 */
@Data
public class ExecModel implements Serializable {

    private static final long serialVersionUID = 2693165196472544096L;

    private String outMsg;

    private String errorMsg;

    private Map<String, String> exceptionMsg = new HashMap<>();

    /**
     * 新增一个异常信息
     *
     * @param exceptionName
     * @param exceptionMsg
     * @return
     */
    public ExecModel addException(String exceptionName, String exceptionMsg) {
        this.exceptionMsg.put(exceptionName, exceptionMsg);
        return this;
    }

    /**
     * 判断命令是否执行成功
     *
     * @return
     */
    public boolean isSuccessful() {
        if (StringUtils.isNotBlank(this.errorMsg)) {
            return false;
        }
        if (exceptionMsg != null) {
            return false;
        }
        return true;
    }
}
