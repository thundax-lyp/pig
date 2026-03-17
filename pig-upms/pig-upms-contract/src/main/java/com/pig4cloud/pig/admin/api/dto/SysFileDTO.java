package com.pig4cloud.pig.admin.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件传输对象
 *
 * @author lengleng
 * @date 2026/03/17
 */
@Data
@Schema(description = "文件")
public class SysFileDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "文件编号")
	private Long id;

	@Schema(description = "文件名")
	private String fileName;

	@Schema(description = "原始文件名")
	private String original;

	@Schema(description = "存储桶名称")
	private String bucketName;

	@Schema(description = "文件类型")
	private String type;

	@Schema(description = "文件大小")
	private Long fileSize;

	@Schema(description = "创建者")
	private String createBy;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "更新者")
	private String updateBy;

	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

	@Schema(description = "删除标记,1:已删除,0:正常")
	private String delFlag;

}
