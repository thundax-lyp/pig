package com.github.thundax.bacon.daemon.quartz.config;

import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * PigQuartz 自定义配置类，用于配置 SchedulerFactoryBean
 *
 */
@Configuration
public class BaconQuartzCustomizerConfig implements SchedulerFactoryBeanCustomizer {

	/**
	 * 自定义SchedulerFactoryBean配置
	 * @param schedulerFactoryBean 调度器工厂bean
	 */
	@Override
	public void customize(SchedulerFactoryBean schedulerFactoryBean) {
		schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
	}

}
