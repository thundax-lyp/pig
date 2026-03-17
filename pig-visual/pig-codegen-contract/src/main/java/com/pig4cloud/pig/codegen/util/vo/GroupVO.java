package com.pig4cloud.pig.codegen.util.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "模板分组视图对象")
public class GroupVO {

	@Schema(description = "id")
	private Long id;

	@Schema(description = "分组名称")
	private String groupName;

	@Schema(description = "分组描述")
	private String groupDesc;

	@Schema(description = "拥有的模板ID列表")
	private Long[] templateId;

	@Schema(description = "拥有的模板列表")
	private List<GenTemplateDTO> templateList;

}
