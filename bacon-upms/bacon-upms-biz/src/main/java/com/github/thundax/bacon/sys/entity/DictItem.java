package com.github.thundax.bacon.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * 字典项
 *
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
public class DictItem extends Model<DictItem> {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;

	private Long dictId;

	private String itemValue;

	private String label;

	private String dictType;

	private String description;

	private Integer sortOrder;

	@TableField(fill = FieldFill.INSERT)
	private String createBy;

	@TableField(fill = FieldFill.UPDATE)
	private String updateBy;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;

	private String remarks;

	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	private String delFlag;

}
