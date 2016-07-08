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
package com.plus.ext.plugin.quartz;

/**
 * @author koo@weteam.me
 * @ClassName: TriggerKit
 * @Description：初始化任务调度常用的时间规则
 * @date 2014年4月28日 上午4:20:17
 */
public enum TriggerKit {
    ice;

    public final String One_Second = "0/1 * * * * ?";
    public final String Five_Second = "0/5 * * * * ?";
    public final String Ten_Second = "0/10 * * * * ?";
    public final String Twenty_Second = "0/20 * * * * ?";
    public final String Thirty_Second = "0/30 * * * * ?";

}