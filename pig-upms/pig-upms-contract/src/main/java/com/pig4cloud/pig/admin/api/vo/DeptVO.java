package com.pig4cloud.pig.admin.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 轻量部门展示对象
 *
 * @author lengleng
 * @date 2026/03/16
 */
@Data
@Schema(description = "部门展示对象")
public class DeptVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "部门id")
	private Long deptId;

	@Schema(description = "部门名称")
	private String name;

	@Schema(description = "父级部门id")
	private Long parentId;

}
