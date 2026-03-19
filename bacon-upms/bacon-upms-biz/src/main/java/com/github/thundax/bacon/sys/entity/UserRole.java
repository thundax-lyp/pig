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
 * 用户角色表
 * </p>
 *
 * @since 2017-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
public class UserRole extends Model<UserRole> {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 角色ID
	 */
	private Long roleId;

}
