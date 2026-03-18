package com.github.thundax.bacon.sys.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典传输对象
 *
 */
@Data
@Schema(description = "字典类型")
public class DictDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "字典编号")
	private Long id;

	@Schema(description = "字典类型")
	private String dictType;

	@Schema(description = "字典描述")
	private String description;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

	@Schema(description = "是否系统内置")
	private String systemFlag;

	@Schema(description = "备注信息")
	private String remarks;

	@Schema(description = "创建人")
	private String createBy;

	@Schema(description = "修改人")
	private String updateBy;

	@Schema(description = "删除标记，1：已删除，0：正常")
	private String delFlag;

}
