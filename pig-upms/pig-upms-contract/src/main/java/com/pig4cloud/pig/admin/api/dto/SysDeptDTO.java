package com.pig4cloud.pig.admin.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 部门传输对象
 *
 * @author lengleng
 * @date 2026/03/17
 */
@Data
@Schema(description = "部门")
public class SysDeptDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "部门id")
	private Long deptId;

	@NotBlank(message = "部门名称不能为空")
	@Schema(description = "部门名称")
	private String name;

	@NotNull(message = "排序值不能为空")
	@Schema(description = "排序值")
	private Integer sortOrder;

	@Schema(description = "创建人")
	private String createBy;

	@Schema(description = "修改人")
	private String updateBy;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "修改时间")
	private LocalDateTime updateTime;

	@Schema(description = "父级部门id")
	private Long parentId;

	@Schema(description = "删除标记,1:已删除,0:正常")
	private String delFlag;

}
