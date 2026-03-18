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
import com.github.thundax.bacon.sys.api.dto.MenuDTO;
import com.github.thundax.bacon.sys.entity.Menu;
import com.github.thundax.bacon.sys.service.MenuService;
import com.github.thundax.bacon.common.core.util.R;
import com.github.thundax.bacon.common.log.annotation.SysLog;
import com.github.thundax.bacon.common.security.annotation.HasPermission;
import com.github.thundax.bacon.common.security.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * 菜单管理控制器
 *
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys/menu")
@Tag(name = "系统：菜单管理模块", description = "提供系统菜单的查询、维护、树结构展示和权限菜单分配能力")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class MenuController {

	private final MenuService sysMenuService;

	/**
	 * 查询当前用户菜单树
	 * @param type 菜单类型
	 * @param parentId 父菜单ID
	 * @return 包含菜单数据的响应对象
	 */
	@GetMapping
	@Operation(summary = "查询当前用户菜单树", description = "用于查询当前用户菜单树")
	public R getUserMenu(String type, Long parentId) {
		// 获取符合条件的菜单
		Set<Menu> all = new HashSet<>();
		SecurityUtils.getRoles().forEach(roleId -> all.addAll(sysMenuService.findMenuByRoleId(roleId)));
		return R.ok(sysMenuService.filterMenu(all, type, parentId));
	}

	/**
	 * 查询菜单树
	 * @param parentId 父节点ID
	 * @param menuName 菜单名称
	 * @param type 菜单类型
	 * @return 包含菜单树数据的响应结果
	 */
	@GetMapping(value = "/tree")
	@Operation(summary = "查询菜单树", description = "用于查询菜单树")
	public R getMenuTree(Long parentId, String menuName, String type) {
		return R.ok(sysMenuService.getMenuTree(parentId, menuName, type));
	}

	/**
	 * 根据角色 ID 查询菜单树
	 * @param roleId 角色 ID
	 * @return 包含菜单 ID 列表的响应结果
	 */
	@GetMapping("/tree/{roleId}")
	@Operation(summary = "根据角色 ID 查询菜单树", description = "用于根据角色 ID 查询菜单树")
	public R getRoleTree(@PathVariable Long roleId) {
		return R.ok(sysMenuService.findMenuByRoleId(roleId).stream().map(Menu::getMenuId).toList());
	}

	/**
	 * 通过 ID 查询菜单详情
	 * @param id 菜单ID
	 * @return 包含菜单详情的响应对象
	 */
	@GetMapping("/{id}")
	@Operation(summary = "通过 ID 查询菜单详情", description = "用于通过 ID 查询菜单详情")
	public R<MenuDTO> getById(@PathVariable Long id) {
		return R.ok(toDto(sysMenuService.getById(id)));
	}

	/**
	 * 新增菜单
	 * @param sysMenu 菜单信息
	 * @return 操作结果
	 */
	@SysLog("新增菜单")
	@PostMapping
	@HasPermission("sys_menu_add")
	@Operation(summary = "新增菜单", description = "用于新增菜单")
	public R<MenuDTO> saveMenu(@Valid @RequestBody MenuDTO sysMenu) {
		Menu entity = new Menu();
		BeanUtil.copyProperties(sysMenu, entity);
		sysMenuService.save(entity);
		return R.ok(toDto(entity));
	}

	/**
	 * 根据菜单ID删除菜单
	 * @param id 要删除的菜单ID
	 * @return 操作结果，成功返回success，失败返回false
	 */
	@SysLog("删除菜单")
	@DeleteMapping("/{id}")
	@HasPermission("sys_menu_del")
	@Operation(summary = "根据菜单 ID 删除菜单", description = "用于根据菜单 ID 删除菜单")
	public R removeById(@PathVariable Long id) {
		return sysMenuService.removeMenuById(id);
	}

	/**
	 * 更新菜单
	 * @param sysMenu 菜单对象
	 * @return 操作结果
	 */
	@SysLog("修改菜单")
	@PutMapping
	@HasPermission("sys_menu_edit")
	@Operation(summary = "修改菜单", description = "用于修改菜单")
	public R updateMenu(@Valid @RequestBody MenuDTO sysMenu) {
		Menu entity = new Menu();
		BeanUtil.copyProperties(sysMenu, entity);
		return R.ok(sysMenuService.updateMenuById(entity));
	}

	private MenuDTO toDto(Menu sysMenu) {
		if (sysMenu == null) {
			return null;
		}
		MenuDTO dto = new MenuDTO();
		BeanUtil.copyProperties(sysMenu, dto);
		return dto;
	}

}
