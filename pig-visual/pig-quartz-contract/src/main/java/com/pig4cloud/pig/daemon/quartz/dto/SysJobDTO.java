package com.pig4cloud.pig.daemon.quartz.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "定时任务")
public class SysJobDTO {

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

	private String misfirePolicy;

	private String jobTenantType;

	private String jobStatus;

	private String jobExecuteStatus;

	private String createBy;

	private String updateBy;

	private LocalDateTime createTime;

	private LocalDateTime updateTime;

	private LocalDateTime startTime;

	private LocalDateTime previousTime;

	private LocalDateTime nextTime;

	private String remark;

}
