package com.pig4cloud.pig.codegen.util.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "模板传输对象")
public class GenTemplateDTO {

	@Schema(description = "主键")
	private Long id;

	@Schema(description = "模板名称")
	private String templateName;

	@Schema(description = "模板路径")
	private String generatorPath;

	@Schema(description = "模板描述")
	private String templateDesc;

	@Schema(description = "模板代码")
	private String templateCode;

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
