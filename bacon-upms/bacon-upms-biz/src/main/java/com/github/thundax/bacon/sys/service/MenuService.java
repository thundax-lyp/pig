package com.github.thundax.bacon.sys.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.thundax.bacon.sys.entity.Menu;
import com.github.thundax.bacon.common.core.util.R;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限服务接口
 * <p>
 * 提供菜单权限相关的服务方法，包括查询、删除、更新和构建菜单树等操作
 * </p>
 *
 */
public interface MenuService extends IService<Menu> {

	/**
	 * 通过角色编号查询URL 权限
	 * @param roleId 角色ID
	 * @return 菜单列表
	 */
	List<Menu> findMenuByRoleId(Long roleId);

	/**
	 * 级联删除菜单
	 * @param id 菜单ID
	 * @return 成功、失败
	 */
	R<?> removeMenuById(Long id);

	/**
	 * 更新菜单信息
	 * @param sysMenu 菜单信息
	 * @return 成功、失败
	 */
	Boolean updateMenuById(Menu sysMenu);

	/**
	 * 构建树查询
	 * @param parentId 父级菜单ID
	 * @param menuName 菜单名称
	 * @param type 类型
	 * @return 菜单树
	 */
	List<Tree<Long>> getMenuTree(Long parentId, String menuName, String type);

	/**
	 * 查询菜单
	 * @param all 全部菜单
	 * @param type 类型
	 * @param parentId 父节点ID
	 * @return list
	 */
	List<Tree<Long>> filterMenu(Set<Menu> all, String type, Long parentId);

}
