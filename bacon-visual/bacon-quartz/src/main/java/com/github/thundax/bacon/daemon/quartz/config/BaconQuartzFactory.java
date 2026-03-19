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
	 */
	@Override
	@SneakyThrows
	public void execute(JobExecutionContext jobExecutionContext) {
		SysJob sysJob = (SysJob) jobExecutionContext.getMergedJobDataMap()
			.get(BaconQuartzEnum.SCHEDULE_JOB_KEY.getType());
		pigQuartzInvokeFactory.init(sysJob, jobExecutionContext.getTrigger());
	}

}
