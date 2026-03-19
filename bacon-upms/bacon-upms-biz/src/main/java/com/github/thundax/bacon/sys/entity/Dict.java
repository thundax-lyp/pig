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
 * 字典表
 *
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
public class Dict extends Model<Dict> {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 类型
	 */
	private String dictType;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 是否是系统内置
	 */
	private String systemFlag;

	/**
	 * 备注信息
	 */
	private String remarks;

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
	 * 删除标记
	 */
	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	private String delFlag;

}
