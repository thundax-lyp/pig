package com.pig4cloud.pig.admin.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 公共参数传输对象
 *
 * @author lengleng
 * @date 2026/03/17
 */
@Data
@Schema(description = "公共参数")
public class SysPublicParamDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "公共参数编号")
	private Long publicId;

	@Schema(description = "公共参数名称")
	private String publicName;

	@Schema(description = "键[英文大写+下划线]")
	private String publicKey;

	@Schema(description = "值")
	private String publicValue;

	@Schema(description = "标识[1有效；2无效]")
	private String status;

	@Schema(description = "编码")
	private String validateCode;

	@Schema(description = "是否是系统内置")
	private String systemFlag;

	@Schema(description = "类型[1-检索；2-原文...]")
	private String publicType;

	@Schema(description = "创建人")
	private String createBy;

	@Schema(description = "修改人")
	private String updateBy;

	@Schema(description = "删除标记,1:已删除,0:正常")
	private String delFlag;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

}
