package com.github.thundax.bacon.sys.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 角色展示对象
 *
 */
@Data
@Schema(description = "角色展示对象")
public class RoleVO {

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 菜单列表
	 */
	private String menuIds;

}
