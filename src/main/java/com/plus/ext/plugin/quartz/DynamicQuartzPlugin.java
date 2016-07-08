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

import com.google.common.base.Throwables;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.IPlugin;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Properties;

/**
 * @ClassName: DynamicQuartzPlugin
 * @Description：动态 Quartz 任务调度，需要配合任务存储
 * @author koo@weteam.me
 * @date 2014年4月18日 下午2:57:43
 * 
 */
@Slf4j
public class DynamicQuartzPlugin implements IPlugin {

	private String config = "job2.properties";

	public static Scheduler sched;
	
	public DynamicQuartzPlugin(String config) {
		this.config = config;
	}

	public DynamicQuartzPlugin() {
	}

	@Override
	public boolean start() {
		try {
			// 加载配置文件
			Properties props = PropKit.use(config).getProperties();

			QuartzKit.INSTANTS.sf = new StdSchedulerFactory(props);

			// 获取Scheduler
			sched = QuartzKit.INSTANTS.sf.getScheduler();
			
			QuartzKit.INSTANTS.scheduler = sched;

            log.info("QuartzKit.INSTANTS.scheduler:" + QuartzKit.INSTANTS.scheduler);

			// 启动
			sched.start();

			return true;
		} catch (Exception e) {
			Throwables.propagate(e);
		}
		return false;
	}

	@Override
	public boolean stop() {
		try {
			sched.shutdown();
			QuartzKit.INSTANTS.sf = null;
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
		}
		return false;
	}
}
