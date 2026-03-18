package com.github.thundax.bacon.sys.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日志写入传输对象
 *
 */
@Data
@Schema(description = "日志")
public class LogRecordDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "日志编号")
	private Long id;

	@NotBlank(message = "日志类型不能为空")
	@Schema(description = "日志类型")
	private String logType;

	@NotBlank(message = "日志标题不能为空")
	@Schema(description = "日志标题")
	private String title;

	@Schema(description = "创建人")
	private String createBy;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

	@Schema(description = "操作ip地址")
	private String remoteAddr;

	@Schema(description = "用户代理")
	private String userAgent;

	@Schema(description = "请求URI")
	private String requestUri;

	@Schema(description = "操作方式")
	private String method;

	@Schema(description = "提交数据")
	private String params;

	@Schema(description = "方法执行时间")
	private Long time;

	@Schema(description = "异常信息")
	private String exception;

	@Schema(description = "应用标识")
	private String serviceId;

	@Schema(description = "删除标记，1：已删除，0：正常")
	private String delFlag;

}
