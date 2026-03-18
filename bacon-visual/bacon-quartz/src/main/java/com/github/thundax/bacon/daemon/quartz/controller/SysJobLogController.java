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
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.thundax.bacon.common.core.util.R;
import com.github.thundax.bacon.daemon.quartz.dto.SysJobLogDTO;
import com.github.thundax.bacon.daemon.quartz.entity.SysJobLog;
import com.github.thundax.bacon.daemon.quartz.service.SysJobLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * 定时任务日志控制器
 *
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys-job-log")
@Tag(description = "sys-job-log", name = "定时任务日志管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysJobLogController {

	private final SysJobLogService sysJobLogService;

	/**
	 * 分页查询定时任务日志
	 * @param page 分页对象
	 * @param sysJobLog 查询条件对象
	 * @return 分页查询结果
	 */
	@GetMapping("/page")
	@Operation(summary = "分页定时任务日志查询", description = "分页定时任务日志查询")
	public R getJobLogPage(Page page, SysJobLogDTO sysJobLog) {
		Page<SysJobLog> jobLogPage = sysJobLogService.page(page, Wrappers.query(toEntity(sysJobLog)));
		Page<SysJobLogDTO> dtoPage = new Page<>(page.getCurrent(), page.getSize(), jobLogPage.getTotal());
		dtoPage.setPages(jobLogPage.getPages());
		dtoPage.setRecords(jobLogPage.getRecords() == null ? new ArrayList<>()
				: jobLogPage.getRecords().stream().map(this::toDto).collect(Collectors.toList()));
		return R.ok(dtoPage);
	}

	/**
	 * 批量删除日志
	 * @param ids 要删除的日志ID数组
	 * @return 操作结果
	 */
	@DeleteMapping
	@Operation(summary = "批量删除日志", description = "批量删除日志")
	public R removeBatchByIds(@RequestBody Long[] ids) {
		return R.ok(sysJobLogService.removeBatchByIds(CollUtil.toList(ids)));
	}

	private SysJobLogDTO toDto(SysJobLog sysJobLog) {
		return sysJobLog == null ? null : BeanUtil.copyProperties(sysJobLog, SysJobLogDTO.class);
	}

	private SysJobLog toEntity(SysJobLogDTO sysJobLog) {
		return sysJobLog == null ? null : BeanUtil.copyProperties(sysJobLog, SysJobLog.class);
	}

}
