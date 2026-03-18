package com.github.thundax.bacon.sys.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 轻量角色展示对象
 *
 */
@Data
@Schema(description = "角色条目展示对象")
public class RoleItemVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "角色ID")
	private Long roleId;

	@Schema(description = "角色名称")
	private String roleName;

	@Schema(description = "角色标识")
	private String roleCode;

}
