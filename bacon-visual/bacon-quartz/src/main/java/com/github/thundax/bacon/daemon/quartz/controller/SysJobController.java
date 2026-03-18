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

package com.github.thundax.bacon.daemon.quartz.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.thundax.bacon.common.core.util.R;
import com.github.thundax.bacon.common.log.annotation.SysLog;
import com.github.thundax.bacon.common.security.annotation.HasPermission;
import com.github.thundax.bacon.common.security.util.SecurityUtils;
import com.github.thundax.bacon.daemon.quartz.constants.JobTypeQuartzEnum;
import com.github.thundax.bacon.daemon.quartz.constants.BaconQuartzEnum;
import com.github.thundax.bacon.daemon.quartz.dto.SysJobDTO;
import com.github.thundax.bacon.daemon.quartz.dto.SysJobLogDTO;
import com.github.thundax.bacon.daemon.quartz.entity.SysJob;
import com.github.thundax.bacon.daemon.quartz.entity.SysJobLog;
import com.github.thundax.bacon.daemon.quartz.service.SysJobLogService;
import com.github.thundax.bacon.daemon.quartz.service.SysJobService;
import com.github.thundax.bacon.daemon.quartz.util.ClassNameValidator;
import com.github.thundax.bacon.daemon.quartz.util.TaskUtil;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 定时任务管理控制器
 *
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/sys-job")
@Tag(description = "sys-job", name = "定时任务管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysJobController {

	private final SysJobService sysJobService;

	private final SysJobLogService sysJobLogService;

	private final TaskUtil taskUtil;

	private final Scheduler scheduler;

	/**
	 * 定时任务分页查询
	 * @param page 分页对象
	 * @param sysJob 定时任务调度表
	 * @return R
	 */
	@GetMapping("/page")
	@Operation(summary = "分页定时业务查询", description = "分页定时业务查询")
	public R getJobPage(Page page, SysJobDTO sysJob) {
		LambdaQueryWrapper<SysJob> wrapper = Wrappers.<SysJob>lambdaQuery()
			.like(StrUtil.isNotBlank(sysJob.getJobName()), SysJob::getJobName, sysJob.getJobName())
			.like(StrUtil.isNotBlank(sysJob.getJobGroup()), SysJob::getJobGroup, sysJob.getJobGroup())
			.eq(StrUtil.isNotBlank(sysJob.getJobStatus()), SysJob::getJobStatus, sysJob.getJobGroup())
			.eq(StrUtil.isNotBlank(sysJob.getJobExecuteStatus()), SysJob::getJobExecuteStatus,
					sysJob.getJobExecuteStatus());
		Page<SysJob> jobPage = sysJobService.page(page, wrapper);
		return R.ok(toJobDtoPage(jobPage));
	}

	/**
	 * 通过id查询定时任务
	 * @param id id
	 * @return R
	 */
	@GetMapping("/{id}")
	@Operation(summary = "唯一标识查询定时任务", description = "唯一标识查询定时任务")
	public R getById(@PathVariable("id") Long id) {
		return R.ok(toJobDto(sysJobService.getById(id)));
	}

	/**
	 * 新增定时任务,默认新增状态为1已发布
	 * @param sysJobDto 定时任务调度表
	 * @return R
	 */
	@SysLog("新增定时任务")
	@PostMapping
	@HasPermission("job_sys_job_add")
	@Operation(summary = "新增定时任务", description = "新增定时任务")
	public R saveJob(@RequestBody SysJobDTO sysJobDto) {
		SysJob sysJob = toJobEntity(sysJobDto);
		long count = sysJobService.count(
				Wrappers.query(SysJob.builder().jobName(sysJob.getJobName()).jobGroup(sysJob.getJobGroup()).build()));

		if (count > 0) {
			return R.failed("任务重复，请检查此组内是否已包含同名任务");
		}

		// 安全验证：对于Java类类型的任务，验证类名和方法名
		if (JobTypeQuartzEnum.JAVA.getType().equals(sysJob.getJobType())) {
			if (!ClassNameValidator.isValidClassName(sysJob.getClassName())) {
				log.warn("新增定时任务失败，类名验证不通过：{}", sysJob.getClassName());
				return R.failed("类名验证失败，该类在黑名单中或包含危险特征，拒绝创建");
			}
			if (!ClassNameValidator.isValidMethodName(sysJob.getMethodName())) {
				log.warn("新增定时任务失败，方法名验证不通过：{}", sysJob.getMethodName());
				return R.failed("方法名验证失败，该方法在黑名单中或包含危险特征，拒绝创建");
			}
		}

		sysJob.setJobStatus(BaconQuartzEnum.JOB_STATUS_RELEASE.getType());
		sysJob.setCreateBy(SecurityUtils.getUser().getUsername());
		return R.ok(sysJobService.save(sysJob));
	}

	/**
	 * 修改定时任务
	 * @param sysJobDto 定时任务调度表
	 * @return R
	 */
	@SysLog("修改定时任务")
	@PutMapping
	@HasPermission("job_sys_job_edit")
	@Operation(summary = "修改定时任务", description = "修改定时任务")
	public R updateJob(@RequestBody SysJobDTO sysJobDto) {
		SysJob sysJob = toJobEntity(sysJobDto);
		// 安全验证：对于Java类类型的任务，验证类名和方法名
		if (JobTypeQuartzEnum.JAVA.getType().equals(sysJob.getJobType())) {
			if (!ClassNameValidator.isValidClassName(sysJob.getClassName())) {
				log.warn("修改定时任务失败，类名验证不通过：{}", sysJob.getClassName());
				return R.failed("类名验证失败，该类在黑名单中或包含危险特征，拒绝修改");
			}
			if (!ClassNameValidator.isValidMethodName(sysJob.getMethodName())) {
				log.warn("修改定时任务失败，方法名验证不通过：{}", sysJob.getMethodName());
				return R.failed("方法名验证失败，该方法在黑名单中或包含危险特征，拒绝修改");
			}
		}

		sysJob.setUpdateBy(SecurityUtils.getUser().getUsername());
		SysJob querySysJob = this.sysJobService.getById(sysJob.getJobId());
		if (BaconQuartzEnum.JOB_STATUS_NOT_RUNNING.getType().equals(querySysJob.getJobStatus())) {
			// 如修改暂停的需更新调度器
			this.taskUtil.addOrUpateJob(sysJob, scheduler);
			sysJobService.updateById(sysJob);
		}
		else if (BaconQuartzEnum.JOB_STATUS_RELEASE.getType().equals(querySysJob.getJobStatus())) {
			sysJobService.updateById(sysJob);
		}
		return R.ok();
	}

	/**
	 * 通过id删除定时任务
	 * @param id 定时任务唯一标识
	 * @return 操作结果
	 * @throws IllegalArgumentException 当任务未暂停时尝试删除会抛出异常
	 */
	@SysLog("删除定时任务")
	@DeleteMapping("/{id}")
	@HasPermission("job_sys_job_del")
	@Operation(summary = "唯一标识查询定时任务，暂停任务才能删除", description = "唯一标识查询定时任务，暂停任务才能删除")
	public R removeById(@PathVariable Long id) {
		SysJob querySysJob = this.sysJobService.getById(id);
		if (BaconQuartzEnum.JOB_STATUS_NOT_RUNNING.getType().equals(querySysJob.getJobStatus())) {
			this.taskUtil.removeJob(querySysJob, scheduler);
			this.sysJobService.removeById(id);
		}
		else if (BaconQuartzEnum.JOB_STATUS_RELEASE.getType().equals(querySysJob.getJobStatus())) {
			this.sysJobService.removeById(id);
		}
		return R.ok();
	}

	/**
	 * 暂停全部定时任务
	 * @return R
	 */
	@SysLog("暂停全部定时任务")
	@PostMapping("/shutdown-jobs")
	@HasPermission("job_sys_job_shutdown_job")
	@Operation(summary = "暂停全部定时任务", description = "暂停全部定时任务")
	public R shutdownJobs() {
		taskUtil.pauseJobs(scheduler);
		long count = this.sysJobService.count(new LambdaQueryWrapper<SysJob>().eq(SysJob::getJobStatus,
				BaconQuartzEnum.JOB_STATUS_RUNNING.getType()));
		if (count <= 0) {
			return R.ok("无正在运行定时任务");
		}
		else {
			// 更新定时任务状态条件，运行状态2更新为暂停状态3
			this.sysJobService.update(
					SysJob.builder().jobStatus(BaconQuartzEnum.JOB_STATUS_NOT_RUNNING.getType()).build(),
					new UpdateWrapper<SysJob>().lambda()
						.eq(SysJob::getJobStatus, BaconQuartzEnum.JOB_STATUS_RUNNING.getType()));
			return R.ok("暂停成功");
		}
	}

	/**
	 * 启动全部定时任务
	 * @return
	 */
	@SysLog("启动全部暂停的定时任务")
	@PostMapping("/start-jobs")
	@HasPermission("job_sys_job_start_job")
	@Operation(summary = "启动全部暂停的定时任务", description = "启动全部暂停的定时任务")
	public R startJobs() {
		// 更新定时任务状态条件，暂停状态3更新为运行状态2
		this.sysJobService.update(SysJob.builder().jobStatus(BaconQuartzEnum.JOB_STATUS_RUNNING.getType()).build(),
				new UpdateWrapper<SysJob>().lambda()
					.eq(SysJob::getJobStatus, BaconQuartzEnum.JOB_STATUS_NOT_RUNNING.getType()));
		taskUtil.startJobs(scheduler);
		return R.ok();
	}

	/**
	 * 刷新全部定时任务 暂停和运行的添加到调度器其他状态从调度器移除
	 * @return R
	 */
	@SysLog("刷新全部定时任务")
	@PostMapping("/refresh-jobs")
	@HasPermission("job_sys_job_refresh_job")
	@Operation(summary = "刷新全部定时任务", description = "刷新全部定时任务")
	public R refreshJobs() {
		sysJobService.list().forEach(sysjob -> {
			if (BaconQuartzEnum.JOB_STATUS_RUNNING.getType().equals(sysjob.getJobStatus())
					|| BaconQuartzEnum.JOB_STATUS_NOT_RUNNING.getType().equals(sysjob.getJobStatus())) {
				taskUtil.addOrUpateJob(sysjob, scheduler);
			}
			else {
				taskUtil.removeJob(sysjob, scheduler);
			}
		});
		return R.ok();
	}

	/**
	 * 启动定时任务
	 * @param jobId 任务id
	 * @return R
	 */
	@SysLog("启动定时任务")
	@PostMapping("/start-job/{id}")
	@HasPermission("job_sys_job_start_job")
	@Operation(summary = "启动定时任务", description = "启动定时任务")
	public R startJob(@PathVariable("id") Long jobId) throws SchedulerException {
		SysJob querySysJob = this.sysJobService.getById(jobId);
		if (querySysJob == null) {
			return R.failed("无此定时任务,请确认");
		}

		// 如果定时任务不存在，强制状态为1已发布
		if (!scheduler.checkExists(TaskUtil.getJobKey(querySysJob))) {
			querySysJob.setJobStatus(BaconQuartzEnum.JOB_STATUS_RELEASE.getType());
			log.warn("定时任务不在quartz中,任务id:{},强制状态为已发布并加入调度器", jobId);
		}

		if (BaconQuartzEnum.JOB_STATUS_RELEASE.getType().equals(querySysJob.getJobStatus())) {
			taskUtil.addOrUpateJob(querySysJob, scheduler);
		}
		else {
			taskUtil.resumeJob(querySysJob, scheduler);
		}
		// 更新定时任务状态为运行状态2
		this.sysJobService
			.updateById(SysJob.builder().jobId(jobId).jobStatus(BaconQuartzEnum.JOB_STATUS_RUNNING.getType()).build());
		return R.ok();
	}

	/**
	 * 启动定时任务
	 * @param jobId 任务id
	 * @return R
	 */
	@SysLog("立刻执行定时任务")
	@PostMapping("/run-job/{id}")
	@HasPermission("job_sys_job_run_job")
	@Operation(summary = "立刻执行定时任务", description = "立刻执行定时任务")
	public R runJob(@PathVariable("id") Long jobId) throws SchedulerException {
		SysJob querySysJob = this.sysJobService.getById(jobId);

		// 执行定时任务前判定任务是否在quartz中
		if (!scheduler.checkExists(TaskUtil.getJobKey(querySysJob))) {
			querySysJob.setJobStatus(BaconQuartzEnum.JOB_STATUS_NOT_RUNNING.getType());
			log.warn("立刻执行定时任务-定时任务不在quartz中,任务id:{},强制状态为暂停并加入调度器", jobId);
			taskUtil.addOrUpateJob(querySysJob, scheduler);
		}

		return TaskUtil.runOnce(scheduler, querySysJob) ? R.ok() : R.failed();
	}

	/**
	 * 暂停定时任务
	 * @return
	 */
	@SysLog("暂停定时任务")
	@PostMapping("/shutdown-job/{id}")
	@HasPermission("job_sys_job_shutdown_job")
	@Operation(summary = "暂停定时任务", description = "暂停定时任务")
	public R shutdownJob(@PathVariable("id") Long id) {
		SysJob querySysJob = this.sysJobService.getById(id);
		// 更新定时任务状态条件，运行状态2更新为暂停状态3
		this.sysJobService.updateById(SysJob.builder()
			.jobId(querySysJob.getJobId())
			.jobStatus(BaconQuartzEnum.JOB_STATUS_NOT_RUNNING.getType())
			.build());
		taskUtil.pauseJob(querySysJob, scheduler);
		return R.ok();
	}

	/**
	 * 分页查询定时执行日志
	 * @param page 分页参数
	 * @param sysJobLog 查询条件
	 * @return 分页结果
	 */
	@GetMapping("/job-log")
	@Operation(summary = "唯一标识查询定时执行日志", description = "唯一标识查询定时执行日志")
	public R getJobLogPage(Page page, SysJobLogDTO sysJobLog) {
		Page<SysJobLog> jobLogPage = sysJobLogService.page(page, Wrappers.query(toJobLogEntity(sysJobLog)));
		return R.ok(toJobLogDtoPage(jobLogPage));
	}

	/**
	 * 校验任务名称和任务组组合是否唯一
	 * @param jobName 任务名称
	 * @param jobGroup 任务组
	 * @return 校验结果，若已存在返回失败信息，否则返回成功
	 */
	@GetMapping("/is-valid-task-name")
	@Operation(summary = "检验任务名称和任务组联合是否唯一", description = "检验任务名称和任务组联合是否唯一")
	public R isValidTaskName(@RequestParam String jobName, @RequestParam String jobGroup) {
		return this.sysJobService
			.count(Wrappers.query(SysJob.builder().jobName(jobName).jobGroup(jobGroup).build())) > 0
					? R.failed("任务重复，请检查此组内是否已包含同名任务") : R.ok();
	}

	/**
	 * 导出任务数据
	 * @param sysJob 查询条件对象
	 * @return 符合条件的任务列表
	 */
	@ResponseExcel
	@GetMapping("/export")
	@Operation(summary = "导出任务", description = "导出任务")
	public List<SysJobDTO> exportJobs(SysJobDTO sysJob) {
		return sysJobService.list(Wrappers.query(toJobEntity(sysJob))).stream().map(this::toJobDto).toList();
	}

	private SysJobDTO toJobDto(SysJob sysJob) {
		return sysJob == null ? null : BeanUtil.copyProperties(sysJob, SysJobDTO.class);
	}

	private SysJob toJobEntity(SysJobDTO sysJob) {
		return sysJob == null ? null : BeanUtil.copyProperties(sysJob, SysJob.class);
	}

	private SysJobLogDTO toJobLogDto(SysJobLog sysJobLog) {
		return sysJobLog == null ? null : BeanUtil.copyProperties(sysJobLog, SysJobLogDTO.class);
	}

	private SysJobLog toJobLogEntity(SysJobLogDTO sysJobLog) {
		return sysJobLog == null ? null : BeanUtil.copyProperties(sysJobLog, SysJobLog.class);
	}

	private Page<SysJobDTO> toJobDtoPage(Page<SysJob> page) {
		Page<SysJobDTO> dtoPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
		dtoPage.setPages(page.getPages());
		dtoPage.setRecords(page.getRecords() == null ? new ArrayList<>()
				: page.getRecords().stream().map(this::toJobDto).collect(Collectors.toList()));
		return dtoPage;
	}

	private Page<SysJobLogDTO> toJobLogDtoPage(Page<SysJobLog> page) {
		Page<SysJobLogDTO> dtoPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
		dtoPage.setPages(page.getPages());
		dtoPage.setRecords(page.getRecords() == null ? new ArrayList<>()
				: page.getRecords().stream().map(this::toJobLogDto).collect(Collectors.toList()));
		return dtoPage;
	}

}
