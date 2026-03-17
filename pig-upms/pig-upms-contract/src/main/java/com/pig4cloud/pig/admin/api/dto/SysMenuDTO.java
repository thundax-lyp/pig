package com.pig4cloud.pig.admin.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单接口传输对象
 *
 * @author lengleng
 * @date 2026/03/17
 */
@Data
@Schema(description = "菜单接口传输对象")
public class SysMenuDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "菜单id")
	private Long menuId;

	@NotBlank(message = "菜单名称不能为空")
	@Schema(description = "菜单名称")
	private String name;

	@Schema(description = "菜单英文名称")
	private String enName;

	@Schema(description = "菜单权限标识")
	private String permission;

	@NotNull(message = "菜单父ID不能为空")
	@Schema(description = "菜单父id")
	private Long parentId;

	@Schema(description = "菜单图标")
	private String icon;

	@Schema(description = "前端路由标识路径")
	private String path;

	@Schema(description = "菜单是否显示")
	private String visible;

	@Schema(description = "排序值")
	private Integer sortOrder;

	@NotNull(message = "菜单类型不能为空")
	@Schema(description = "菜单类型,0:菜单 1:按钮")
	private String menuType;

	@Schema(description = "路由缓冲")
	private String keepAlive;

	@Schema(description = "菜单是否内嵌")
	private String embedded;

	@Schema(description = "创建人")
	private String createBy;

	@Schema(description = "修改人")
	private String updateBy;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

	@Schema(description = "删除标记,1:已删除,0:正常")
	private String delFlag;

}
