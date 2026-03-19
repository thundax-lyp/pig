package com.github.thundax.bacon.daemon.quartz.util;

import com.github.thundax.bacon.daemon.quartz.entity.SysJob;
import com.github.thundax.bacon.daemon.quartz.exception.TaskException;

/**
 * 定时任务反射实现接口
 *
 */
public interface ITaskInvok {

	/**
	 * 执行反射方法
	 * @param sysJob 任务配置类
	 * @throws TaskException 执行任务时可能抛出的异常
	 */
	void invokMethod(SysJob sysJob) throws TaskException;

}
