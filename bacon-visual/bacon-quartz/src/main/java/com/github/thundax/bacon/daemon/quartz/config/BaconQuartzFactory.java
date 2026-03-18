/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.github.thundax.bacon.daemon.quartz.config;

import com.github.thundax.bacon.daemon.quartz.constants.BaconQuartzEnum;
import com.github.thundax.bacon.daemon.quartz.entity.SysJob;
import lombok.SneakyThrows;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 动态任务工厂：用于执行动态任务调度
 *
 */
@DisallowConcurrentExecution
public class BaconQuartzFactory implements Job {

	/**
	 * 定时任务调用工厂
	 */
	@Autowired
	private BaconQuartzInvokeFactory pigQuartzInvokeFactory;

	/**
	 * 执行定时任务
	 * @param jobExecutionContext 任务执行上下文
	 * @throws Exception 执行过程中可能抛出的异常
	 */
	@Override
	@SneakyThrows
	public void execute(JobExecutionContext jobExecutionContext) {
		SysJob sysJob = (SysJob) jobExecutionContext.getMergedJobDataMap()
			.get(BaconQuartzEnum.SCHEDULE_JOB_KEY.getType());
		pigQuartzInvokeFactory.init(sysJob, jobExecutionContext.getTrigger());
	}

}
