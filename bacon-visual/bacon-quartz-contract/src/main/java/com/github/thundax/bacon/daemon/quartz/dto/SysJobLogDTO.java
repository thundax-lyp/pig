package com.github.thundax.bacon.daemon.quartz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "定时任务日志")
public class SysJobLogDTO {

	private Long jobLogId;

	private Long jobId;

	private String jobName;

	private String jobGroup;

	private String jobOrder;

	private String jobType;

	private String executePath;

	private String className;

	private String methodName;

	private String methodParamsValue;

	private String cronExpression;

	private String jobMessage;

	private String jobLogStatus;

	private String executeTime;

	private String exceptionInfo;

	private LocalDateTime createTime;

}
