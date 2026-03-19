package com.github.thundax.bacon.daemon.quartz.config;

import com.github.thundax.bacon.daemon.quartz.constants.BaconQuartzEnum;
import com.github.thundax.bacon.daemon.quartz.service.SysJobService;
import com.github.thundax.bacon.daemon.quartz.util.TaskUtil;
import lombok.AllArgsConstructor;
import org.quartz.Scheduler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * 初始化加载定时任务配置类
 *
 */
@Configuration
@AllArgsConstructor
public class BaconInitQuartzJob implements InitializingBean {

	private final SysJobService sysJobService;

	private final TaskUtil taskUtil;

	private final Scheduler scheduler;

	/**
	 * 在属性设置完成后执行，根据任务状态进行相应操作
	 */
	@Override
	public void afterPropertiesSet() {
		sysJobService.list().forEach(sysjob -> {
			if (BaconQuartzEnum.JOB_STATUS_RELEASE.getType().equals(sysjob.getJobStatus())) {
				taskUtil.removeJob(sysjob, scheduler);
			}
			else if (BaconQuartzEnum.JOB_STATUS_RUNNING.getType().equals(sysjob.getJobStatus())) {
				taskUtil.resumeJob(sysjob, scheduler);
			}
			else if (BaconQuartzEnum.JOB_STATUS_NOT_RUNNING.getType().equals(sysjob.getJobStatus())) {
				taskUtil.pauseJob(sysjob, scheduler);
			}
			else {
				taskUtil.removeJob(sysjob, scheduler);
			}
		});
	}

}
