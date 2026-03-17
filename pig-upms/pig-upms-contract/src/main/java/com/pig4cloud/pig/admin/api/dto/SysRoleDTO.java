package com.pig4cloud.pig.admin.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色接口传输对象
 *
 * @author lengleng
 * @date 2026/03/17
 */
@Data
@Schema(description = "角色接口传输对象")
public class SysRoleDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "角色编号")
	private Long roleId;

	@NotBlank(message = "角色名称不能为空")
	@Schema(description = "角色名称")
	private String roleName;

	@NotBlank(message = "角色标识不能为空")
	@Schema(description = "角色标识")
	private String roleCode;

	@Schema(description = "角色描述")
	private String roleDesc;

	@Schema(description = "创建人")
	private String createBy;

	@Schema(description = "修改人")
	private String updateBy;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "修改时间")
	private LocalDateTime updateTime;

	@Schema(description = "删除标记,1:已删除,0:正常")
	private String delFlag;

}
