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

package com.pig4cloud.pig.sys.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.sys.api.dto.SysPublicParamDTO;
import com.pig4cloud.pig.sys.entity.SysPublicParam;
import com.pig4cloud.pig.sys.service.SysPublicParamService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.annotation.HasPermission;
import com.pig4cloud.pig.common.security.annotation.Inner;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公共参数控制器：提供公共参数的增删改查及同步功能
 *
 * @author lengleng
 * @date 2025/05/30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys/param")
@Tag(name = "系统：公共参数配置管理模块", description = "提供系统公共参数的查询、维护、导出和缓存同步能力")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysPublicParamController {

	private final SysPublicParamService sysPublicParamService;

	/**
	 * 根据参数键查询公共参数值
	 * @param publicKey 公共参数键
	 * @return 公共参数值
	 */
	@Inner(value = false)
	@Operation(summary = "根据参数键查询公共参数值", description = "用于根据参数键查询公共参数值")
	@GetMapping("/publicValue/{publicKey}")
	public R publicKey(@PathVariable("publicKey") String publicKey) {
		return R.ok(sysPublicParamService.getParamValue(publicKey));
	}

	/**
	 * 分页查询系统公共参数
	 * @param page 分页对象
	 * @param sysPublicParam 公共参数查询条件
	 * @return 分页查询结果
	 */
	@GetMapping("/page")
	@Operation(summary = "分页查询公共参数", description = "用于分页查询公共参数")
	public R<IPage<SysPublicParamDTO>> getParamPage(@ParameterObject Page<SysPublicParam> page,
			@ParameterObject SysPublicParamDTO sysPublicParam) {
		LambdaUpdateWrapper<SysPublicParam> wrapper = Wrappers.<SysPublicParam>lambdaUpdate()
			.like(StrUtil.isNotBlank(sysPublicParam.getPublicName()), SysPublicParam::getPublicName,
					sysPublicParam.getPublicName())
			.like(StrUtil.isNotBlank(sysPublicParam.getPublicKey()), SysPublicParam::getPublicKey,
					sysPublicParam.getPublicKey())
			.eq(StrUtil.isNotBlank(sysPublicParam.getSystemFlag()), SysPublicParam::getSystemFlag,
					sysPublicParam.getSystemFlag());

		IPage<SysPublicParam> result = sysPublicParamService.page(page, wrapper);
		Page<SysPublicParamDTO> dtoPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
		dtoPage.setRecords(result.getRecords().stream().map(this::toDto).toList());
		return R.ok(dtoPage);
	}

	/**
	 * 通过 ID 查询公共参数
	 * @param publicId 公共参数 ID
	 * @return 包含查询结果的响应对象
	 */
	@Operation(summary = "通过 ID 查询公共参数", description = "用于通过 ID 查询公共参数")
	@GetMapping("/details/{publicId}")
	public R<SysPublicParamDTO> getById(@PathVariable("publicId") Long publicId) {
		return R.ok(toDto(sysPublicParamService.getById(publicId)));
	}

	/**
	 * 查询系统公共参数详情
	 * @param param 系统公共参数查询对象
	 * @return 包含查询结果的响应对象
	 */
	@GetMapping("/details")
	@Operation(summary = "查询系统公共参数详情", description = "用于查询系统公共参数详情")
	public R<SysPublicParamDTO> getDetail(@ParameterObject SysPublicParamDTO param) {
		SysPublicParam entity = new SysPublicParam();
		BeanUtil.copyProperties(param, entity);
		return R.ok(toDto(sysPublicParamService.getOne(Wrappers.query(entity), false)));
	}

	/**
	 * 新增公共参数
	 * @param sysPublicParam 公共参数对象
	 * @return 操作结果
	 */
	@PostMapping
	@SysLog("新增公共参数")
	@Operation(summary = "新增公共参数", description = "用于新增公共参数")
	@HasPermission("sys_syspublicparam_add")
	public R saveParam(@RequestBody SysPublicParamDTO sysPublicParam) {
		SysPublicParam entity = new SysPublicParam();
		BeanUtil.copyProperties(sysPublicParam, entity);
		return R.ok(sysPublicParamService.save(entity));
	}

	/**
	 * 修改公共参数
	 * @param sysPublicParam 公共参数对象
	 * @return 操作结果
	 */
	@PutMapping
	@SysLog("修改公共参数")
	@HasPermission("sys_syspublicparam_edit")
	@Operation(summary = "修改公共参数", description = "用于修改公共参数")
	public R updateParam(@RequestBody SysPublicParamDTO sysPublicParam) {
		SysPublicParam entity = new SysPublicParam();
		BeanUtil.copyProperties(sysPublicParam, entity);
		return sysPublicParamService.updateParam(entity);
	}

	/**
	 * 删除公共参数
	 * @param ids 要删除的公共参数 ID 数组
	 * @return 操作结果
	 */
	@DeleteMapping
	@SysLog("删除公共参数")
	@HasPermission("sys_syspublicparam_del")
	@Operation(summary = "删除公共参数", description = "用于删除公共参数")
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(sysPublicParamService.removeParamByIds(ids));
	}

	/**
	 * 导出excel 表格
	 * @return
	 */
	@ResponseExcel
	@GetMapping("/export")
	@HasPermission("sys_syspublicparam_edit")
	@Operation(summary = "导出公共参数", description = "用于导出公共参数")
	public List<SysPublicParamDTO> exportParams() {
		return sysPublicParamService.list().stream().map(this::toDto).toList();
	}

	/**
	 * 同步参数到缓存
	 * @return 操作结果
	 */
	@SysLog("同步参数")
	@PutMapping("/sync")
	@HasPermission("sys_syspublicparam_edit")
	@Operation(summary = "同步参数到缓存", description = "用于同步参数到缓存")
	public R syncParam() {
		return sysPublicParamService.syncParamCache();
	}

	private SysPublicParamDTO toDto(SysPublicParam sysPublicParam) {
		if (sysPublicParam == null) {
			return null;
		}
		return BeanUtil.copyProperties(sysPublicParam, SysPublicParamDTO.class);
	}

}
