package com.pig4cloud.pig.sys.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 字典项传输对象
 *
 * @author lengleng
 * @date 2026/03/16
 */
@Data
@Schema(description = "字典项")
public class DictItemDTO {

	@Schema(description = "字典项ID")
	private Long id;

	@Schema(description = "所属字典类ID")
	private Long dictId;

	@Schema(description = "数据值")
	@JsonProperty("value")
	private String itemValue;

	@Schema(description = "标签名")
	private String label;

	@Schema(description = "类型")
	private String dictType;

	@Schema(description = "描述")
	private String description;

	@Schema(description = "排序值，默认升序")
	private Integer sortOrder;

	@Schema(description = "创建人")
	private String createBy;

	@Schema(description = "修改人")
	private String updateBy;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

	@Schema(description = "备注信息")
	private String remarks;

	@Schema(description = "删除标记，1：已删除，0：正常")
	private String delFlag;

}
