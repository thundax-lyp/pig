package com.github.thundax.bacon.sys.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.thundax.bacon.sys.entity.RoleMenu;
import com.github.thundax.bacon.sys.mapper.RoleMenuMapper;
import com.github.thundax.bacon.sys.service.RoleMenuService;
import com.github.thundax.bacon.common.core.constant.CacheConstants;
import com.github.thundax.bacon.common.core.support.JetCacheVersionSupport;
import com.github.thundax.bacon.common.security.service.UserDetailsCacheService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * 角色菜单表服务实现类
 *
 */
@Service
@AllArgsConstructor
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

	private final UserDetailsCacheService userDetailsCacheService;

	private final JetCacheVersionSupport jetCacheVersionSupport;

	/**
	 * @param roleId 角色
	 * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheInvalidate(name = CacheConstants.MENU_DETAILS + ":",
			key = "@jetCacheVersionSupport.versionedKey('" + CacheConstants.MENU_DETAILS + "', #roleId)")
	public Boolean saveRoleMenus(Long roleId, String menuIds) {
		this.remove(Wrappers.<RoleMenu>query().lambda().eq(RoleMenu::getRoleId, roleId));

		if (StrUtil.isBlank(menuIds)) {
			return Boolean.TRUE;
		}
		List<RoleMenu> roleMenuList = Arrays.stream(menuIds.split(StrUtil.COMMA)).map(menuId -> {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(Long.valueOf(menuId));
			return roleMenu;
		}).toList();

		// 清空userinfo
		userDetailsCacheService.clear();
		this.saveBatch(roleMenuList);
		return Boolean.TRUE;
	}

}
