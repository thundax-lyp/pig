package com.pig4cloud.pig.admin.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 岗位接口传输对象
 *
 * @author lengleng
 * @date 2026/03/17
 */
@Data
@Schema(description = "岗位接口传输对象")
public class SysPostDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "岗位ID")
	private Long postId;

	@NotBlank(message = "岗位编码不能为空")
	@Schema(description = "岗位编码")
	private String postCode;

	@NotBlank(message = "岗位名称不能为空")
	@Schema(description = "岗位名称")
	private String postName;

	@NotNull(message = "排序值不能为空")
	@Schema(description = "岗位排序")
	private Integer postSort;

	@Schema(description = "岗位描述")
	private String remark;

	@Schema(description = "创建人")
	private String createBy;

	@Schema(description = "修改人")
	private String updateBy;

	@Schema(description = "是否删除  -1：已删除  0：正常")
	private String delFlag;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

}
