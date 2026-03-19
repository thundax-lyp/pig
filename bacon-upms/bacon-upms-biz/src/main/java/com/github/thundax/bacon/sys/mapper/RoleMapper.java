package com.github.thundax.bacon.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.thundax.bacon.sys.entity.Role;
import com.github.thundax.bacon.sys.api.vo.RoleItemVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @since 2017-10-29
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 通过用户ID查询角色信息
	 * @param userId 用户ID
	 * @return 角色信息列表
	 */
	List<Role> listRolesByUserId(Long userId);

	/**
	 * 通过用户ID查询轻量角色展示对象
	 * @param userId 用户ID
	 * @return 角色展示对象列表
	 */
	List<RoleItemVO> listRoleVosByUserId(Long userId);

}
