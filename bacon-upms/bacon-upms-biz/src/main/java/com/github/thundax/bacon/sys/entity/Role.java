package com.github.thundax.bacon.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @since 2017-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
public class Role extends Model<Role> {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "role_id", type = IdType.ASSIGN_ID)
	private Long roleId;

	private String roleName;

	private String roleCode;

	private String roleDesc;

	/**
	 * 创建人
	 */
	@TableField(fill = FieldFill.INSERT)
	private String createBy;

	/**
	 * 修改人
	 */
	@TableField(fill = FieldFill.UPDATE)
	private String updateBy;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 删除标识（0-正常,1-删除）
	 */
	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	private String delFlag;

}
