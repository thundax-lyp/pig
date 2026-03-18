package com.github.thundax.bacon.codegen.util.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "模板分组传输对象")
public class TemplateGroupDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "id")
	private Long id;

	@Schema(description = "分组名称")
	private String groupName;

	@Schema(description = "分组描述")
	private String groupDesc;

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

	@Schema(description = "模板id集合")
	private List<Long> templateId;

}
