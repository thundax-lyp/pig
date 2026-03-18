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
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.thundax.bacon.sys.api.dto.RoleDTO;
import com.github.thundax.bacon.sys.entity.Role;
import com.github.thundax.bacon.sys.api.vo.RoleExcelVO;
import com.github.thundax.bacon.sys.api.vo.RoleVO;
import com.github.thundax.bacon.sys.service.RoleService;
import com.github.thundax.bacon.common.core.constant.CacheConstants;
import com.github.thundax.bacon.common.core.support.JetCacheVersionSupport;
import com.github.thundax.bacon.common.core.util.R;
import com.github.thundax.bacon.common.log.annotation.SysLog;
import com.github.thundax.bacon.common.security.annotation.HasPermission;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理控制器：提供角色相关的增删改查及权限管理功能
 *
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys/role")
@Tag(name = "系统：角色管理模块", description = "提供系统角色的查询、维护、授权、导入导出等管理能力")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class RoleController {

	private final RoleService sysRoleService;

	private final JetCacheVersionSupport jetCacheVersionSupport;

	/**
	 * 通过 ID 查询角色信息
	 * @param id 角色ID
	 * @return 包含角色信息的响应对象
	 */
	@GetMapping("/details/{id}")
	@Operation(summary = "通过 ID 查询角色信息", description = "用于通过 ID 查询角色信息")
	public R<RoleDTO> getById(@PathVariable Long id) {
		return R.ok(toDto(sysRoleService.getById(id)));
	}

	/**
	 * 查询角色详情
	 * @param query 角色查询条件对象
	 * @return 包含角色信息的响应结果
	 */
	@GetMapping("/details")
	@Operation(summary = "查询角色详情", description = "用于查询角色详情")
	public R<RoleDTO> getDetails(@ParameterObject RoleDTO query) {
		Role sysRole = new Role();
		BeanUtil.copyProperties(query, sysRole);
		return R.ok(toDto(sysRoleService.getOne(Wrappers.query(sysRole), false)));
	}

	/**
	 * 添加角色
	 * @param sysRole 角色信息
	 * @return 操作结果，成功返回success，失败返回false
	 */
	@SysLog("新增角色")
	@PostMapping
	@HasPermission("sys_role_add")
	@Operation(summary = "新增角色", description = "用于新增角色")
	public R saveRole(@Valid @RequestBody RoleDTO sysRole) {
		Role entity = new Role();
		BeanUtil.copyProperties(sysRole, entity);
		R result = R.ok(sysRoleService.save(entity));
		jetCacheVersionSupport.increment(CacheConstants.ROLE_DETAILS);
		return result;
	}

	/**
	 * 修改角色
	 * @param sysRole 角色信息
	 * @return 操作结果，成功返回success，失败返回false
	 */
	@SysLog("修改角色")
	@PutMapping
	@HasPermission("sys_role_edit")
	@Operation(summary = "修改角色", description = "用于修改角色")
	public R updateRole(@Valid @RequestBody RoleDTO sysRole) {
		Role entity = new Role();
		BeanUtil.copyProperties(sysRole, entity);
		R result = R.ok(sysRoleService.updateById(entity));
		jetCacheVersionSupport.increment(CacheConstants.ROLE_DETAILS);
		return result;
	}

	/**
	 * 根据ID数组删除角色
	 * @param ids 角色ID数组
	 * @return 操作结果
	 */
	@SysLog("删除角色")
	@DeleteMapping
	@HasPermission("sys_role_del")
	@Operation(summary = "根据 ID 数组删除角色", description = "用于根据 ID 数组删除角色")
	public R removeById(@RequestBody Long[] ids) {
		R result = R.ok(sysRoleService.removeRoleByIds(ids));
		jetCacheVersionSupport.increment(CacheConstants.ROLE_DETAILS);
		return result;
	}

	/**
	 * 查询角色列表
	 * @return 包含角色列表的响应结果
	 */
	@GetMapping("/list")
	@Operation(summary = "查询角色列表", description = "用于查询角色列表")
	public R<List<RoleDTO>> listRoles() {
		return R.ok(sysRoleService.list(Wrappers.emptyWrapper()).stream().map(this::toDto).toList());
	}

	/**
	 * 分页查询角色
	 * @param page 分页对象
	 * @param role 查询条件对象
	 * @return 包含分页结果的响应对象
	 */
	@GetMapping("/page")
	@Operation(summary = "分页查询角色", description = "用于分页查询角色")
	public R<IPage<RoleDTO>> getRolePage(@ParameterObject Page<Role> page, @ParameterObject RoleDTO role) {
		IPage<Role> result = sysRoleService.page(page, Wrappers.<Role>lambdaQuery()
			.like(StrUtil.isNotBlank(role.getRoleName()), Role::getRoleName, role.getRoleName()));
		Page<RoleDTO> dtoPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
		dtoPage.setRecords(result.getRecords().stream().map(this::toDto).toList());
		return R.ok(dtoPage);
	}

	/**
	 * 修改角色菜单
	 * @param roleVo 角色VO对象
	 * @return 操作结果，成功返回success，失败返回false
	 */
	@SysLog("修改角色菜单")
	@PutMapping("/menu")
	@HasPermission("sys_role_perm")
	@Operation(summary = "修改角色菜单", description = "用于修改角色菜单")
	public R saveRoleMenus(@RequestBody RoleVO roleVo) {
		return R.ok(sysRoleService.updateRoleMenus(roleVo));
	}

	/**
	 * 通过角色 ID 列表查询角色信息
	 * @param roleIdList 角色 ID 列表
	 * @return 包含查询结果的响应对象
	 */
	@PostMapping("/getRoleList")
	@Operation(summary = "通过角色 ID 列表查询角色信息", description = "用于通过角色 ID 列表查询角色信息")
	public R getRoleList(@RequestBody List<Long> roleIdList) {
		return R.ok(sysRoleService.listRolesByRoleIds(roleIdList, CollUtil.join(roleIdList, StrUtil.UNDERLINE)));
	}

	/**
	 * 导出角色数据到 Excel
	 * @return 角色数据列表
	 */
	@ResponseExcel
	@GetMapping("/export")
	@HasPermission("sys_role_export")
	@Operation(summary = "导出角色数据到 Excel", description = "用于导出角色数据到 Excel")
	public List<RoleExcelVO> exportRoles() {
		return sysRoleService.listRoles();
	}

	/**
	 * 导入角色
	 * @param excelVOList 角色Excel数据列表
	 * @param bindingResult 数据校验结果
	 * @return 导入结果
	 */
	@PostMapping("/import")
	@HasPermission("sys_role_export")
	@Operation(summary = "导入角色数据", description = "用于导入角色数据")
	public R importRole(@RequestExcel List<RoleExcelVO> excelVOList, BindingResult bindingResult) {
		return sysRoleService.importRole(excelVOList, bindingResult);
	}

	private RoleDTO toDto(Role sysRole) {
		if (sysRole == null) {
			return null;
		}
		RoleDTO dto = new RoleDTO();
		BeanUtil.copyProperties(sysRole, dto);
		return dto;
	}

}
