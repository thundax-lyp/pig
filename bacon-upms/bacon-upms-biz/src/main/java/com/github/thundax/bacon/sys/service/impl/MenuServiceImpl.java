package com.github.thundax.bacon.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.thundax.bacon.sys.entity.Menu;
import com.github.thundax.bacon.sys.entity.RoleMenu;
import com.github.thundax.bacon.sys.mapper.MenuMapper;
import com.github.thundax.bacon.sys.mapper.RoleMenuMapper;
import com.github.thundax.bacon.sys.service.MenuService;
import com.github.thundax.bacon.common.core.constant.CacheConstants;
import com.github.thundax.bacon.common.core.constant.CommonConstants;
import com.github.thundax.bacon.common.core.constant.enums.MenuTypeEnum;
import com.github.thundax.bacon.common.core.exception.ErrorCodes;
import com.github.thundax.bacon.common.core.support.JetCacheVersionSupport;
import com.github.thundax.bacon.common.core.util.MsgUtils;
import com.github.thundax.bacon.common.core.util.R;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 菜单权限表服务实现类
 *
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

	private final RoleMenuMapper sysRoleMenuMapper;

	private final JetCacheVersionSupport jetCacheVersionSupport;

	/**
	 * 根据角色ID查询菜单列表
	 * @param roleId 角色ID
	 * @return 菜单列表，如果结果为空则不会被缓存
	 * @see CacheConstants#MENU_DETAILS
	 */
	@Override
	@Cached(name = CacheConstants.MENU_DETAILS + ":",
			key = "@jetCacheVersionSupport.versionedKey('" + CacheConstants.MENU_DETAILS + "', #roleId)", expire = 30,
			timeUnit = TimeUnit.MINUTES, cacheType = CacheType.REMOTE,
			postCondition = "#result != null && !#result.isEmpty()")
	public List<Menu> findMenuByRoleId(Long roleId) {
		return baseMapper.listMenusByRoleId(roleId);
	}

	/**
	 * 根据ID删除菜单
	 * @param id 菜单ID
	 * @return 删除结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R removeMenuById(Long id) {
		// 查询父节点为当前节点的节点
		List<Menu> menuList = this.list(Wrappers.<Menu>query().lambda().eq(Menu::getParentId, id));
		if (CollUtil.isNotEmpty(menuList)) {
			return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_MENU_DELETE_EXISTING));
		}

		sysRoleMenuMapper.delete(Wrappers.<RoleMenu>query().lambda().eq(RoleMenu::getMenuId, id));
		// 删除当前菜单及其子菜单
		R result = R.ok(this.removeById(id));
		jetCacheVersionSupport.increment(CacheConstants.MENU_DETAILS);
		return result;
	}

	/**
	 * 根据ID更新菜单信息
	 * @param sysMenu 菜单实体对象
	 * @return 更新是否成功
	 */
	@Override
	public Boolean updateMenuById(Menu sysMenu) {
		Boolean result = this.updateById(sysMenu);
		jetCacheVersionSupport.increment(CacheConstants.MENU_DETAILS);
		return result;
	}

	/**
	 * 构建菜单树结构
	 * @param parentId 父节点ID，为空时使用默认根节点
	 * @param menuName 菜单名称，支持模糊查询
	 * @param type 菜单类型
	 * @return 菜单树结构列表，模糊查询时返回平铺列表
	 */
	@Override
	public List<Tree<Long>> getMenuTree(Long parentId, String menuName, String type) {
		Long parent = parentId == null ? CommonConstants.MENU_TREE_ROOT_ID : parentId;

		List<TreeNode<Long>> collect = baseMapper
			.selectList(Wrappers.<Menu>lambdaQuery()
				.like(StrUtil.isNotBlank(menuName), Menu::getName, menuName)
				.eq(StrUtil.isNotBlank(type), Menu::getMenuType, type)
				.orderByAsc(Menu::getSortOrder))
			.stream()
			.map(getNodeFunction())
			.toList();

		// 模糊查询 不组装树结构 直接返回 表格方便编辑
		if (StrUtil.isNotBlank(menuName)) {
			return collect.stream().map(node -> {
				Tree<Long> tree = new Tree<>();
				tree.putAll(node.getExtra());
				BeanUtils.copyProperties(node, tree);
				return tree;
			}).toList();
		}

		return TreeUtil.build(collect, parent);
	}

	/**
	 * 根据类型和父节点ID过滤菜单并构建树形结构
	 * @param all 全部菜单集合
	 * @param type 菜单类型
	 * @param parentId 父节点ID，为空时使用根节点ID
	 * @return 构建好的菜单树形结构列表
	 */
	@Override
	public List<Tree<Long>> filterMenu(Set<Menu> all, String type, Long parentId) {
		List<TreeNode<Long>> collect = all.stream().filter(menuTypePredicate(type)).map(getNodeFunction()).toList();

		Long parent = parentId == null ? CommonConstants.MENU_TREE_ROOT_ID : parentId;
		return TreeUtil.build(collect, parent);
	}

	/**
	 * 获取将Menu转换为TreeNode<Long>的函数
	 * @return 转换函数，将Menu对象转换为TreeNode<Long>对象
	 */
	@NotNull
	private Function<Menu, TreeNode<Long>> getNodeFunction() {
		return menu -> {
			TreeNode<Long> node = new TreeNode<>();
			node.setId(menu.getMenuId());
			node.setName(menu.getName());
			node.setParentId(menu.getParentId());
			node.setWeight(menu.getSortOrder());
			// 扩展属性
			Map<String, Object> extra = new HashMap<>();
			extra.put(Menu.Fields.path, menu.getPath());
			extra.put(Menu.Fields.menuType, menu.getMenuType());
			extra.put(Menu.Fields.permission, menu.getPermission());
			extra.put(Menu.Fields.sortOrder, menu.getSortOrder());

			// 适配 vue3
			Map<String, Object> meta = new HashMap<>();
			meta.put("title", menu.getName());
			meta.put("isLink", menu.getPath() != null && menu.getPath().startsWith("http") ? menu.getPath() : "");
			meta.put("isHide", !BooleanUtil.toBooleanObject(menu.getVisible()));
			meta.put("isKeepAlive", BooleanUtil.toBooleanObject(menu.getKeepAlive()));
			meta.put("isAffix", false);
			meta.put("isIframe", BooleanUtil.toBooleanObject(menu.getEmbedded()));
			meta.put(Menu.Fields.icon, menu.getIcon());
			// 增加英文
			meta.put(Menu.Fields.enName, menu.getEnName());

			extra.put("meta", meta);
			node.setExtra(extra);
			return node;
		};
	}

	/**
	 * menu 类型断言
	 * @param type 类型
	 * @return Predicate
	 */
	private Predicate<Menu> menuTypePredicate(String type) {
		return vo -> {
			if (MenuTypeEnum.TOP_MENU.getDescription().equals(type)) {
				return MenuTypeEnum.TOP_MENU.getType().equals(vo.getMenuType());
			}
			// 其他查询 左侧 + 顶部
			return !MenuTypeEnum.BUTTON.getType().equals(vo.getMenuType());
		};
	}

}
