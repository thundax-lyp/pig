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
import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.sys.api.dto.SysDictDTO;
import com.pig4cloud.pig.sys.api.dto.SysDictItemDTO;
import com.pig4cloud.pig.sys.entity.SysDict;
import com.pig4cloud.pig.sys.entity.SysDictItem;
import com.pig4cloud.pig.sys.service.SysDictItemService;
import com.pig4cloud.pig.sys.service.SysDictService;
import com.pig4cloud.pig.common.core.constant.CacheConstants;
import com.pig4cloud.pig.common.core.support.JetCacheVersionSupport;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.annotation.Inner;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 字典表前端控制器
 *
 * @author lengleng
 * @date 2025/05/30
 * @since 2019-03-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys/dict")
@Tag(name = "系统：字典管理模块", description = "提供系统字典类型与字典项的查询、维护、导出和缓存同步能力")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysDictController {

	private final SysDictService sysDictService;

	private final SysDictItemService sysDictItemService;

	private final JetCacheVersionSupport jetCacheVersionSupport;

	/**
	 * 通过 ID 查询字典信息
	 * @param id 字典ID
	 * @return 包含字典信息的响应对象
	 */
	@GetMapping("/details/{id}")
	@Operation(summary = "通过 ID 查询字典信息", description = "用于通过 ID 查询字典信息")
	public R<SysDictDTO> getById(@PathVariable Long id) {
		return R.ok(toDictDto(sysDictService.getById(id)));
	}

	/**
	 * 查询字典详情
	 * @param query 字典查询条件对象
	 * @return 包含字典信息的响应结果
	 */
	@GetMapping("/details")
	@Operation(summary = "查询字典详情", description = "用于查询字典详情")
	public R<SysDictDTO> getDetails(@ParameterObject SysDictDTO query) {
		SysDict sysDict = new SysDict();
		BeanUtil.copyProperties(query, sysDict);
		return R.ok(toDictDto(sysDictService.getOne(Wrappers.query(sysDict), false)));
	}

	/**
	 * 分页查询字典
	 * @param page 分页对象
	 * @param sysDict 字典查询条件
	 * @return 包含分页结果的响应对象
	 */
	@GetMapping("/page")
	@Operation(summary = "分页查询字典", description = "用于分页查询字典")
	public R<IPage<SysDictDTO>> getDictPage(@ParameterObject Page<SysDict> page, @ParameterObject SysDictDTO sysDict) {
		IPage<SysDict> result = sysDictService.page(page,
				Wrappers.<SysDict>lambdaQuery()
					.eq(StrUtil.isNotBlank(sysDict.getSystemFlag()), SysDict::getSystemFlag, sysDict.getSystemFlag())
					.like(StrUtil.isNotBlank(sysDict.getDictType()), SysDict::getDictType, sysDict.getDictType()));
		Page<SysDictDTO> dtoPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
		dtoPage.setRecords(result.getRecords().stream().map(this::toDictDto).toList());
		return R.ok(dtoPage);
	}

	/**
	 * 新增字典
	 * @param sysDict 字典信息对象
	 * @return 操作结果，包含新增的字典信息
	 */
	@SysLog("新增字典")
	@PostMapping
	@Operation(summary = "新增字典", description = "用于新增字典")
	@PreAuthorize("@pms.hasPermission('sys_dict_add')")
	public R<SysDictDTO> saveDict(@Valid @RequestBody SysDictDTO sysDict) {
		SysDict entity = new SysDict();
		BeanUtil.copyProperties(sysDict, entity);
		sysDictService.save(entity);
		jetCacheVersionSupport.increment(CacheConstants.DICT_DETAILS);
		return R.ok(toDictDto(entity));
	}

	/**
	 * 删除字典并清除字典缓存
	 * @param ids 字典ID数组
	 * @return 操作结果
	 */
	@SysLog("删除字典")
	@DeleteMapping
	@PreAuthorize("@pms.hasPermission('sys_dict_del')")
	@Operation(summary = "删除字典并清除字典缓存", description = "用于删除字典并清除字典缓存")
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(sysDictService.removeDictByIds(ids));
	}

	/**
	 * 修改字典
	 * @param sysDict 字典信息
	 * @return 操作结果 success/false
	 */
	@PutMapping
	@SysLog("修改字典")
	@PreAuthorize("@pms.hasPermission('sys_dict_edit')")
	@Operation(summary = "修改字典", description = "用于修改字典")
	public R updateDict(@Valid @RequestBody SysDictDTO sysDict) {
		SysDict entity = new SysDict();
		BeanUtil.copyProperties(sysDict, entity);
		return sysDictService.updateDict(entity);
	}

	/**
	 * 分页查询字典列表
	 * @param name 字典类型名称或描述
	 * @return 包含字典列表的响应结果
	 */
	@GetMapping("/list")
	@Operation(summary = "分页查询字典列表", description = "用于分页查询字典列表")
	public R<List<SysDictDTO>> listDicts(String name) {
		return R.ok(sysDictService.list(Wrappers.<SysDict>lambdaQuery()
			.like(StrUtil.isNotBlank(name), SysDict::getDictType, name)
			.or()
			.like(StrUtil.isNotBlank(name), SysDict::getDescription, name)).stream().map(this::toDictDto).toList());
	}

	/**
	 * 分页查询字典项
	 * @param page 分页对象
	 * @param sysDictItem 字典项查询条件
	 * @return 分页查询结果
	 */
	@GetMapping("/item/page")
	@Operation(summary = "分页查询字典项", description = "用于分页查询字典项")
	public R<IPage<SysDictItemDTO>> getDictItemPage(@ParameterObject Page<SysDictItem> page,
			@ParameterObject SysDictItemDTO sysDictItem) {
		SysDictItem entity = new SysDictItem();
		BeanUtil.copyProperties(sysDictItem, entity);
		IPage<SysDictItem> result = sysDictItemService.page(page, Wrappers.query(entity));
		Page<SysDictItemDTO> dtoPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
		dtoPage.setRecords(result.getRecords().stream().map(this::toDictItemDto).toList());
		return R.ok(dtoPage);
	}

	/**
	 * 通过 ID 查询字典项详情
	 * @param id 字典项id
	 * @return 包含字典项详情的响应结果
	 */
	@GetMapping("/item/details/{id}")
	@Operation(summary = "通过 ID 查询字典项详情", description = "用于通过 ID 查询字典项详情")
	public R<SysDictItemDTO> getDictItemById(@PathVariable("id") Long id) {
		return R.ok(toDictItemDto(sysDictItemService.getById(id)));
	}

	/**
	 * 查询字典项详情
	 * @param query 字典项查询条件
	 * @return 包含字典项详情的响应结果
	 */
	@GetMapping("/item/details")
	@Operation(summary = "查询字典项详情", description = "用于查询字典项详情")
	public R<SysDictItemDTO> getDictItemDetails(@ParameterObject SysDictItemDTO query) {
		SysDictItem entity = new SysDictItem();
		BeanUtil.copyProperties(query, entity);
		return R.ok(toDictItemDto(sysDictItemService.getOne(Wrappers.query(entity), false)));
	}

	/**
	 * 新增字典项
	 * @param sysDictItem 字典项对象
	 * @return 操作结果
	 */
	@SysLog("新增字典项")
	@PostMapping("/item")
	@Operation(summary = "新增字典项", description = "用于新增字典项")
	public R saveDictItem(@RequestBody SysDictItemDTO sysDictItem) {
		SysDictItem entity = new SysDictItem();
		BeanUtil.copyProperties(sysDictItem, entity);
		R result = R.ok(sysDictItemService.save(entity));
		jetCacheVersionSupport.increment(CacheConstants.DICT_DETAILS);
		return result;
	}

	/**
	 * 修改字典项
	 * @param sysDictItem 要修改的字典项对象
	 * @return 操作结果
	 */
	@SysLog("修改字典项")
	@PutMapping("/item")
	@Operation(summary = "修改字典项", description = "用于修改字典项")
	public R updateDictItem(@RequestBody SysDictItemDTO sysDictItem) {
		SysDictItem entity = new SysDictItem();
		BeanUtil.copyProperties(sysDictItem, entity);
		return sysDictItemService.updateDictItem(entity);
	}

	/**
	 * 通过 ID 删除字典项
	 * @param id 字典项id
	 * @return 操作结果
	 */
	@SysLog("删除字典项")
	@DeleteMapping("/item/{id}")
	@Operation(summary = "通过 ID 删除字典项", description = "用于通过 ID 删除字典项")
	public R removeDictItemById(@PathVariable Long id) {
		return sysDictItemService.removeDictItem(id);
	}

	/**
	 * 同步字典缓存
	 * @return 操作结果
	 */
	@SysLog("同步字典缓存")
	@PutMapping("/sync")
	@Operation(summary = "同步字典缓存", description = "用于同步字典缓存")
	public R syncDict() {
		return sysDictService.syncDictCache();
	}

	/**
	 * 导出字典项数据
	 * @param sysDictItem 字典项查询条件
	 * @return 符合条件的字典项列表
	 */
	@ResponseExcel
	@GetMapping("/export")
	@Operation(summary = "导出字典项数据", description = "用于导出字典项数据")
	public List<SysDictItemDTO> exportDictItems(@ParameterObject SysDictItemDTO sysDictItem) {
		SysDictItem entity = new SysDictItem();
		BeanUtil.copyProperties(sysDictItem, entity);
		return sysDictItemService.list(Wrappers.query(entity)).stream().map(this::toDictItemDto).toList();
	}

	/**
	 * 通过字典类型查找字典
	 * @param type 类型
	 * @return 同类型字典
	 */
	@GetMapping("/type/{type}")
	@Operation(summary = "通过字典类型查询字典", description = "用于通过字典类型查询字典")
	@Cached(name = CacheConstants.DICT_DETAILS + ":",
			key = "@jetCacheVersionSupport.versionedKey('" + CacheConstants.DICT_DETAILS + "', #type)",
			expire = 1, timeUnit = TimeUnit.HOURS, cacheType = CacheType.REMOTE,
			postCondition = "#result != null && #result.data != null && !#result.data.isEmpty()")
	public R<List<SysDictItemDTO>> getDictByType(@PathVariable String type) {
		return R.ok(sysDictItemService.list(Wrappers.<SysDictItem>query().lambda().eq(SysDictItem::getDictType, type))
			.stream()
			.map(item -> BeanUtil.copyProperties(item, SysDictItemDTO.class))
			.collect(Collectors.toList()));
	}

	/**
	 * 通过字典类型查询字典（Feign 调用）TODO: 兼容性方案，代码重复
	 * @param type 类型
	 * @return 同类型字典
	 */
	@Inner
	@GetMapping("/remote/type/{type}")
	@Operation(summary = "通过字典类型查询字典（Feign 调用）", description = "用于通过字典类型查询字典（Feign 调用）", hidden = true)
	@Cached(name = CacheConstants.DICT_DETAILS + ":",
			key = "@jetCacheVersionSupport.versionedKey('" + CacheConstants.DICT_DETAILS + "', #type)",
			expire = 1, timeUnit = TimeUnit.HOURS, cacheType = CacheType.REMOTE,
			postCondition = "#result != null && #result.data != null && !#result.data.isEmpty()")
	public R<List<SysDictItemDTO>> getRemoteDictByType(@PathVariable String type) {
		return R.ok(sysDictItemService.list(Wrappers.<SysDictItem>query().lambda().eq(SysDictItem::getDictType, type))
			.stream()
			.map(item -> BeanUtil.copyProperties(item, SysDictItemDTO.class))
			.collect(Collectors.toList()));
	}

	private SysDictDTO toDictDto(SysDict sysDict) {
		if (sysDict == null) {
			return null;
		}
		return BeanUtil.copyProperties(sysDict, SysDictDTO.class);
	}

	private SysDictItemDTO toDictItemDto(SysDictItem sysDictItem) {
		if (sysDictItem == null) {
			return null;
		}
		return BeanUtil.copyProperties(sysDictItem, SysDictItemDTO.class);
	}

}
