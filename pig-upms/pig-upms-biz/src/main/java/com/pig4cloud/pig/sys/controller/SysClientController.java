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

package com.pig4cloud.pig.sys.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.sys.api.dto.SysOauthClientDetailsDTO;
import com.pig4cloud.pig.sys.entity.SysOauthClientDetails;
import com.pig4cloud.pig.sys.service.SysOauthClientDetailsService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.annotation.HasPermission;
import com.pig4cloud.pig.common.security.annotation.Inner;
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
 * 客户端管理模块前端控制器
 *
 * @author lengleng
 * @date 2025/05/30
 * @since 2018-05-15
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys/client")
@Tag(name = "系统：客户端管理模块", description = "提供系统客户端的查询、维护、导出和缓存同步能力")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysClientController {

	private final SysOauthClientDetailsService clientDetailsService;

	/**
	 * 通过客户端ID查询客户端详情
	 * @param clientId 客户端ID
	 * @return 包含客户端详情的响应对象
	 */
	@GetMapping("/{clientId}")
	@Operation(summary = "通过客户端 ID 查询客户端详情", description = "用于通过客户端 ID 查询客户端详情")
	public R<SysOauthClientDetailsDTO> getByClientId(@PathVariable String clientId) {
		SysOauthClientDetails details = clientDetailsService
			.getOne(Wrappers.<SysOauthClientDetails>lambdaQuery().eq(SysOauthClientDetails::getClientId, clientId));
		return R.ok(toDto(details));
	}

	/**
	 * 分页查询客户端
	 * @param page 分页参数对象
	 * @param sysOauthClientDetails 客户端查询条件
	 * @return 分页查询结果
	 */
	@GetMapping("/page")
	@Operation(summary = "分页查询客户端", description = "用于分页查询客户端")
	public R<IPage<SysOauthClientDetailsDTO>> getClientPage(@ParameterObject Page<SysOauthClientDetails> page,
			@ParameterObject SysOauthClientDetailsDTO sysOauthClientDetails) {
		LambdaQueryWrapper<SysOauthClientDetails> wrapper = Wrappers.<SysOauthClientDetails>lambdaQuery()
			.like(StrUtil.isNotBlank(sysOauthClientDetails.getClientId()), SysOauthClientDetails::getClientId,
					sysOauthClientDetails.getClientId())
			.like(StrUtil.isNotBlank(sysOauthClientDetails.getClientSecret()), SysOauthClientDetails::getClientSecret,
					sysOauthClientDetails.getClientSecret());
		IPage<SysOauthClientDetails> result = clientDetailsService.page(page, wrapper);
		Page<SysOauthClientDetailsDTO> dtoPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
		dtoPage.setRecords(result.getRecords().stream().map(this::toDto).toList());
		return R.ok(dtoPage);
	}

	/**
	 * 新增客户端
	 * @param clientDetails 客户端对象
	 * @return 操作结果，成功返回success，失败返回false
	 */
	@SysLog("新增客户端")
	@PostMapping
	@HasPermission("sys_client_add")
	@Operation(summary = "新增客户端", description = "用于新增客户端")
	public R saveClient(@Valid @RequestBody SysOauthClientDetailsDTO clientDetails) {
		SysOauthClientDetails entity = new SysOauthClientDetails();
		BeanUtil.copyProperties(clientDetails, entity);
		return R.ok(clientDetailsService.saveClient(entity));
	}

	/**
	 * 根据 ID 列表删除客户端
	 * @param ids 要删除的客户端 ID 数组
	 * @return 操作结果，成功返回success
	 */
	@SysLog("删除客户端")
	@DeleteMapping
	@HasPermission("sys_client_del")
	@Operation(summary = "根据 ID 列表删除客户端", description = "用于根据 ID 列表删除客户端")
	public R removeById(@RequestBody Long[] ids) {
		clientDetailsService.removeBatchByIds(CollUtil.toList(ids));
		return R.ok();
	}

	/**
	 * 修改客户端
	 * @param clientDetails 客户端对象
	 * @return 操作结果
	 */
	@SysLog("修改客户端")
	@PutMapping
	@HasPermission("sys_client_edit")
	@Operation(summary = "修改客户端", description = "用于修改客户端")
	public R updateClient(@Valid @RequestBody SysOauthClientDetailsDTO clientDetails) {
		SysOauthClientDetails entity = new SysOauthClientDetails();
		BeanUtil.copyProperties(clientDetails, entity);
		return R.ok(clientDetailsService.updateClientById(entity));
	}

	/**
	 * 根据客户端 ID 查询客户端详情
	 * @param clientId 客户端 ID
	 * @return 包含客户端详情的响应结果
	 */
	@Inner
	@GetMapping("/getClientDetailsById/{clientId}")
	@Operation(summary = "根据客户端 ID 查询客户端详情", description = "用于根据客户端 ID 查询客户端详情")
	public R getClientDetailsById(@PathVariable String clientId) {
		SysOauthClientDetails clientDetails = clientDetailsService.getOne(
				Wrappers.<SysOauthClientDetails>lambdaQuery().eq(SysOauthClientDetails::getClientId, clientId), false);
		return R.ok(toDto(clientDetails));
	}

	/**
	 * 同步客户端缓存
	 * @return 操作结果
	 */
	@SysLog("同步客户端缓存")
	@PutMapping("/sync")
	@Operation(summary = "同步客户端缓存", description = "用于同步客户端缓存")
	public R syncClient() {
		return clientDetailsService.syncClientCache();
	}

	/**
	 * 导出客户端数据到 Excel
	 * @param sysOauthClientDetails 客户端查询条件
	 * @return 符合条件的客户端列表
	 */
	@ResponseExcel
	@SysLog("导出客户端数据")
	@GetMapping("/export")
	@Operation(summary = "导出客户端数据到 Excel", description = "用于导出客户端数据到 Excel")
	public List<SysOauthClientDetailsDTO> exportClients(@ParameterObject SysOauthClientDetailsDTO sysOauthClientDetails) {
		SysOauthClientDetails entity = new SysOauthClientDetails();
		BeanUtil.copyProperties(sysOauthClientDetails, entity);
		return clientDetailsService.list(Wrappers.query(entity)).stream().map(this::toDto).toList();
	}

	private SysOauthClientDetailsDTO toDto(SysOauthClientDetails clientDetails) {
		if (clientDetails == null) {
			return null;
		}
		return BeanUtil.copyProperties(clientDetails, SysOauthClientDetailsDTO.class);
	}

}
