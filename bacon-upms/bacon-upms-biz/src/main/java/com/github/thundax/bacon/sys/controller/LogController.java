/*
 *
 *      Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */

package com.github.thundax.bacon.sys.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.thundax.bacon.sys.api.dto.LogDTO;
import com.github.thundax.bacon.sys.api.dto.LogRecordDTO;
import com.github.thundax.bacon.sys.entity.Log;
import com.github.thundax.bacon.sys.api.vo.LogExcelVO;
import com.github.thundax.bacon.sys.service.LogService;
import com.github.thundax.bacon.common.core.util.R;
import com.github.thundax.bacon.common.security.annotation.HasPermission;
import com.github.thundax.bacon.common.security.annotation.Inner;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统日志前端控制器
 *
 * @since 2017-11-20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys/log")
@Tag(name = "系统：日志管理模块", description = "提供系统日志的查询、写入、导出和清理能力")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class LogController {

	private final LogService logService;

	/**
	 * 分页查询系统日志
	 * @param page 分页参数对象
	 * @param sysLog 系统日志查询条件
	 * @return 包含分页结果的响应对象
	 */
	@GetMapping("/page")
	@Operation(summary = "分页查询系统日志", description = "用于分页查询系统日志")
	public R getLogPage(@ParameterObject Page page, @ParameterObject LogDTO sysLog) {
		return R.ok(logService.getLogPage(page, sysLog));
	}

	/**
	 * 批量删除日志
	 * @param ids 要删除的日志ID数组
	 * @return 操作结果，成功返回success，失败返回false
	 */
	@DeleteMapping
	@HasPermission("sys_log_del")
	@Operation(summary = "批量删除日志", description = "用于批量删除日志")
	public R removeByIds(@RequestBody Long[] ids) {
		return R.ok(logService.removeBatchByIds(CollUtil.toList(ids)));
	}

	/**
	 * 保存日志
	 * @param sysLog 日志实体
	 * @return 操作结果，成功返回success，失败返回false
	 */
	@Inner
	@PostMapping("/save")
	@Operation(summary = "保存日志", description = "用于保存日志")
	public R saveLog(@Valid @RequestBody LogRecordDTO sysLog) {
		return R.ok(logService.saveLog(BeanUtil.copyProperties(sysLog, Log.class)));
	}

	/**
	 * 导出系统日志到Excel表格
	 * @param sysLog 系统日志查询条件DTO
	 * @return 符合查询条件的系统日志列表
	 */
	@ResponseExcel
	@GetMapping("/export")
	@HasPermission("sys_log_export")
	@Operation(summary = "导出系统日志到 Excel", description = "用于导出系统日志到 Excel")
	public List<LogExcelVO> exportLogs(LogDTO sysLog) {
		return logService.listLogs(sysLog).stream().map(log -> BeanUtil.copyProperties(log, LogExcelVO.class)).toList();
	}

}
