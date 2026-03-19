package com.github.thundax.bacon.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.thundax.bacon.sys.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @since 2017-10-29
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 通过角色编号查询菜单
	 * @param roleId 角色ID
	 */
	List<Menu> listMenusByRoleId(Long roleId);

}
