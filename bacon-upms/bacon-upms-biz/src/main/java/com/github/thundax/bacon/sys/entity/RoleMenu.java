package com.github.thundax.bacon.sys.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.io.Serial;

/**
 * <p>
 * 角色菜单表
 * </p>
 *
 * @since 2017-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
public class RoleMenu extends Model<RoleMenu> {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 菜单ID
	 */
	private Long menuId;

}
