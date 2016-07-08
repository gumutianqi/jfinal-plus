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
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @ClassName: QuartzKit
 * @Description：动态任务调度工具Kit
 * @author koo@weteam.me
 * @date 2014年4月18日 下午3:11:52
 * 
 */
public enum QuartzKit {

	INSTANTS;
	
	public SchedulerFactory sf;

	public Scheduler scheduler;
	
//	/**
//	 * 创建一个调度对象
//	 * 
//	 * @return
//	 */
//	private Scheduler getThisScheduler() {
//		System.out.println("sf0:" + sf);
//		try {
//			if(null != sf) {
//				scheduler = sf.getScheduler();
//			}
//		} catch (SchedulerException e) {
//			Throwables.propagate(e);
//		}
//		System.out.println("sf1:" + sf);
//		return scheduler;
//	}
	
	/**
	 * @Title: createJobDetail
	 * @Description: 创建一个JobDetail
	 * @param id	唯一标识
	 * @param name	JobName
	 * @param group	分组
	 * @param jobClass	要工作JobClass
	 * @return
	 * 
	 * @return JobDetail
	 * @throws
	 */
	public JobDetail createJobDetail(String id, String name, String group, Class<? extends Job> jobClass) {
		JobDetail job = newJob(jobClass)
			.withIdentity("job_" + name + "_" + id, "group_" + group)
			.build();
		
		job.getJobDataMap().put("jobId", id);
		
		return job;
	}
	
	/**
	 * @Title: createJobDetail
	 * @Description: 创建一个JobDetail, 携带desc描述信息
	 * @param id	唯一标识
	 * @param name	JobName
	 * @param group	分组
	 * @param desc	描述信息
	 * @param jobClass	要工作JobClass
	 * @return
	 * 
	 * @return JobDetail
	 * @throws
	 */
	public JobDetail createJobDetail(String id, String name, String group, String desc, Class<? extends Job> jobClass) {
		JobDetail job = newJob(jobClass)
				.withIdentity("job_" + name + "_" + id, "group_" + group)
				.withDescription(desc)
				.build();
		
		job.getJobDataMap().put("jobId", id);
		
		return job;
	}
	
	/**
	 * @Title: createJobDetail
	 * @Description: 创建一个JobDetail, 携带desc描述信息和JobDateMap数据
	 * @param id	唯一标识
	 * @param name	JobName
	 * @param group	分组
	 * @param desc	描述信息
	 * @param newJobDataMap	 要在Job存储和传递的参数，Key-Value类型
	 * @param jobClass	要工作JobClass
	 * @return
	 * 
	 * @return JobDetail
	 * @throws
	 */
	public JobDetail createJobDetail(String id, String name, String group, String desc, JobDataMap newJobDataMap, Class<? extends Job> jobClass) {
		JobDetail job = newJob(jobClass)
				.withIdentity("job_" + name + "_" + id, "group_" + group)
				.withDescription(desc)
				.setJobData(newJobDataMap)
				.build();
		
		job.getJobDataMap().put("jobId", id);
		
		return job;
	}
	
	/**
	 * 创建一个简单任务，单次执行
	 */
	/**
	 * @Title: createOnceTrigger
	 * @Description: 只执行一次的Trigger
	 * @param startTime	开始时间
	 * @param id	唯一表示
	 * @param name	TriggerName
	 * @param group	分组
	 * @return
	 * 
	 * @return SimpleTrigger
	 * @throws
	 */
	public SimpleTrigger createOnceTrigger(Date startTime, String id, String name, String group) {
		SimpleTrigger trigger = (SimpleTrigger) newTrigger()
				.withIdentity("trigger_" + name + "_" + id, "group_" + group)
				.startAt(startTime)
				.build();
		
		return trigger;
	}
	
	/**
	 * @Title: createOnceTrigger
	 * @Description: 只执行一次的Trigger
	 * @param startTime
	  * @param id	唯一表示
	 * @param name	TriggerName
	 * @param group	分组
	 * @param desc	描述信息
	 * @return
	 * 
	 * @return SimpleTrigger
	 * @throws
	 */
	public SimpleTrigger createOnceTrigger(Date startTime, String id, String name, String group, String desc) {
		SimpleTrigger trigger = (SimpleTrigger) newTrigger()
				.withIdentity("trigger_" + name + "_" + id, "group_" + group)
				.startAt(startTime)
				.withDescription(desc)
				.build();
		
		return trigger;
	}
	
	/*
	 * 创建一个Trigger，包含开始时间和结束时间
	 */
	/**
	 * @Title: createTrigger
	 * @Description: TODO
	 * @param startTime
	 * @param endTime
	 * @param cronExpression
	  * @param id	唯一表示
	 * @param name	TriggerName
	 * @param group	分组
	 * @return
	 * 
	 * @return SimpleTrigger
	 * @throws
	 */
	public SimpleTrigger createTrigger(Date startTime, Date endTime, String cronExpression, String id, String name, String group) {
		SimpleTrigger trigger = (SimpleTrigger) newTrigger()
				.withIdentity("trigger_" + name + "_" + id, "group_" + group)
				.startAt(startTime)
				.endAt(endTime)
				.withSchedule(cronSchedule(cronExpression))
				.build();
		
		return trigger;
	}
	
	/**
	 * @Title: createTrigger
	 * @Description: 创建一个Trigger
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @param cronExpression	cron表达式
	 * @param id	唯一表示
	 * @param name	TriggerName
	 * @param group	分组
	 * @param desc	描述信息
	 * @return
	 * 
	 * @return SimpleTrigger
	 * @throws
	 */
	public SimpleTrigger createTrigger(Date startTime, Date endTime, String cronExpression, String id, String name, String group, String desc) {
		SimpleTrigger trigger = (SimpleTrigger) newTrigger()
				.withIdentity("trigger_" + name + "_" + id, "group_" + group)
				.startAt(startTime)
				.endAt(endTime)
				.withDescription(desc)
				.withSchedule(cronSchedule(cronExpression))
				.build();
		
		return trigger;
	}
	
	/**
	 * @Title: createTrigger
	 * @Description: 创建一个Trigger
	 * @param id	表示ID
	 * @param name	TriggerName
	 * @param group	分组信息
	 * @param cronExpression	cron表达式
	 * @return
	 * 
	 * @return Trigger
	 * @throws
	 */
	public Trigger createTrigger(String id, String name, String group, String cronExpression) {
		Trigger trigger = newTrigger()
				.withIdentity("trigger_" + name + "_" + id, "group_" + group)
				.withSchedule(cronSchedule(cronExpression))
				.build();
		
		return trigger;
	}
	
	/**
	 * @Title: createTrigger
	 * @Description: 创建一个Trigger
	 * @param id	标识ID
	 * @param name	TriggerName
	 * @param group	分组信息
	 * @param desc	描述信息
	 * @param cronExpression	cron表达式
	 * @return
	 * 
	 * @return Trigger
	 * @throws
	 */
	public Trigger createTrigger(String id, String name, String group, String desc, String cronExpression) {
		Trigger trigger = newTrigger()
				.withIdentity("trigger_" + name + "_" + id, "group_" + group)
				.withSchedule(cronSchedule(cronExpression))
				.withDescription(desc)
				.build();
		
		return trigger;
	}
	
	
	/*
	 * =====================================================================
	 * ========== 通过标识 Id,Name,Group 获取JobDetail和Trigger对象 ===========
	 * =====================================================================
	 */
	
	/*
	 * 得到一个Trigger
	 */
	public Trigger findTrigger(String id, String name, String group) {
		Trigger trigger;
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey("trigger_" + name + "_" + id, "group_" + group);
			trigger = scheduler.getTrigger(triggerKey);
			return trigger;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return null;
		}
	}
	
	/*
	 * 得到一个TriggerKey
	 */
	public TriggerKey findTriggerKey(String id, String name, String group) {
		TriggerKey triggerKey = TriggerKey.triggerKey("trigger_" + name + "_" + id, "group_" + group);
		return triggerKey;
	}
	
	/*
	 * 得到一个JobDetail
	 */
	public JobDetail findJobDetail(String id, String name, String group) {
		JobDetail jobDetail;
		try {
			JobKey jobKey = JobKey.jobKey("job_" + name + "_" + id, "group_" + group);
			jobDetail = scheduler.getJobDetail(jobKey);
			return jobDetail;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return null;
		}
	}
	
	/*
	 * 得到一个JobKey
	 */
	public JobKey findJobKey(String id, String name, String group) {
		JobKey jobKey = JobKey.jobKey("job_" + name + "_" + id, "group_" + group);
		return jobKey;
	}
	
	
	
	/**
	 * =========================================================
	 * ================ 封装 Scheduler 常用工具 ==================
	 * =========================================================
	 */
	
	/**
	 * 添加调度的job信息
	 * 
	 * @param jobdetail
	 * @param trigger
	 * @return
	 */
	public Date scheduleJob(JobDetail jobdetail, Trigger trigger) {
		try {
			return scheduler.scheduleJob(jobdetail, trigger);
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return null;
		}
	}

	/**
	 * 添加相关的触发器
	 * 
	 * @param trigger
	 * @return
	 */
	public Date scheduleJob(Trigger trigger) {
		try {
			return scheduler.scheduleJob(trigger);
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return null;
		}
	}

	/**
	 * 添加多个job任务
	 * 
	 * @param triggersAndJobs
	 * @param replace
	 */
	public Boolean scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace) {
		try {
			scheduler.scheduleJobs(triggersAndJobs, replace);
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}

	}

	/**
	 * 停止调度Job任务
	 * 
	 * @param triggerkey
	 * @return
	 */
	public Boolean unscheduleJob(TriggerKey triggerkey) {
		try {
			return scheduler.unscheduleJob(triggerkey);
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

	/**
	 * 停止调度多个触发器相关的job
	 * 
	 * @param list
	 * @return
	 */
	public Boolean unscheduleJobs(List<TriggerKey> triggerKeylist) {
		try {
			return scheduler.unscheduleJobs(triggerKeylist);
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

	/**
	 * 重新恢复触发器相关的job任务
	 * 
	 * @param triggerkey
	 * @param trigger
	 * @return
	 */
	public Date rescheduleJob(TriggerKey triggerkey, Trigger trigger) {
		try {
			return scheduler.rescheduleJob(triggerkey, trigger);
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return null;
		}
	}

	/**
	 * 添加相关的job任务
	 * 
	 * @param jobdetail
	 * @param flag
	 */
	public Boolean addJob(JobDetail jobdetail, boolean flag) {
		try {
			scheduler.addJob(jobdetail, flag);
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}

	}

	/**
	 * 删除相关的job任务
	 * 
	 * @param jobkey
	 * @return
	 */
	public Boolean deleteJob(JobKey jobkey) {
		try {
			return scheduler.deleteJob(jobkey);
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

    /**
     * 检查Job是否存在
     * @param jobkey
     * @return
     */
	public Boolean checkExistsJob(JobKey jobkey) {
		try {
			return scheduler.checkExists(jobkey);
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

    /**
     * 检查Trigger是否存在
     * @param triggerkey
     * @return
     */
	public Boolean checkExistsTrigger(TriggerKey triggerkey) {
		try {
			return scheduler.checkExists(triggerkey);
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

	/**
	 * 删除相关的多个job任务
	 * 
	 * @param jobKeys
	 * @return
	 */
	public Boolean deleteJobs(List<JobKey> jobKeys) {
		try {
			return scheduler.deleteJobs(jobKeys);
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

	/**
	 * 
	 * @param jobkey
	 */
	public Boolean triggerJob(JobKey jobkey) {
		try {
			scheduler.triggerJob(jobkey);
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}

	}

	/**
	 * 
	 * @param jobkey
	 * @param jobdatamap
	 */
	public Boolean triggerJob(JobKey jobkey, JobDataMap jobdatamap) {
		try {
			scheduler.triggerJob(jobkey, jobdatamap);
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

	/**
	 * 暂停一个job任务
	 * 
	 * @param jobkey
	 */
	public Boolean pauseJob(JobKey jobkey) {
		try {
			scheduler.pauseJob(jobkey);
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

	/**
	 * 暂停多个job任务
	 * 
	 * @param groupmatcher
	 */
	public Boolean pauseJobs(GroupMatcher<JobKey> groupmatcher) {
		try {
			scheduler.pauseJobs(groupmatcher);
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

	/**
	 * 暂停使用相关的触发器
	 * 
	 * @param triggerkey
	 */
	public Boolean pauseTrigger(TriggerKey triggerkey) {
		try {
			scheduler.pauseTrigger(triggerkey);
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

	public Boolean pauseTriggers(GroupMatcher<TriggerKey> groupmatcher) {
		try {
			scheduler.pauseTriggers(groupmatcher);
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

	/**
	 * 恢复相关的job任务
	 * 
	 * @param jobkey
	 */
	public Boolean resumeJob(JobKey jobkey) {
		try {
			scheduler.resumeJob(jobkey);
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

	public Boolean resumeJobs(GroupMatcher<JobKey> matcher) {
		try {
			scheduler.resumeJobs(matcher);
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

	public Boolean resumeTrigger(TriggerKey triggerkey) {
		try {
			scheduler.resumeTrigger(triggerkey);
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

	public Boolean resumeTriggers(GroupMatcher<TriggerKey> groupmatcher) {
		try {
			scheduler.resumeTriggers(groupmatcher);
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

	/**
	 * 暂停调度中所有的job任务
	 * 
	 */
	public Boolean pauseAll() {
		try {
			scheduler.pauseAll();
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

	/**
	 * 恢复调度中所有的job的任务
	 * 
	 */
	public Boolean resumeAll() {
		try {
			scheduler.resumeAll();
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}
	
	/**
	 * 启动一个调度对象
	 * 
	 */
	public Boolean start() {
		try {
			scheduler.start();
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

	/**
	 * 检查调度是否启动
	 * 
	 * @return
	 */
	public Boolean isStarted() {
		try {
			return scheduler.isStarted();
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}
	}

	/**
	 * 关闭调度信息
	 * 
	 */
	public Boolean shutdown() {
		try {
			if(!scheduler.isShutdown()) {
				scheduler.shutdown();
			}
			return true;
		} catch (SchedulerException e) {
			Throwables.propagate(e);
			return false;
		}

	}

}
