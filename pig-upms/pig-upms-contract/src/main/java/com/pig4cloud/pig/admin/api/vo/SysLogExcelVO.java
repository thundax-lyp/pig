package com.pig4cloud.pig.admin.api.vo;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日志导出对象
 *
 * @author lengleng
 * @date 2026/03/17
 */
@Data
public class SysLogExcelVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@ExcelProperty("日志编号")
	private Long id;

	@ExcelProperty("日志类型（0-正常 9-错误）")
	private String logType;

	@ExcelProperty("日志标题")
	private String title;

	@ExcelProperty("创建人")
	private String createBy;

	@ExcelProperty("创建时间")
	private LocalDateTime createTime;

	@ExcelIgnore
	private LocalDateTime updateTime;

	@ExcelProperty("操作ip地址")
	private String remoteAddr;

	private String userAgent;

	@ExcelProperty("浏览器")
	private String requestUri;

	@ExcelProperty("操作方式")
	private String method;

	@ExcelProperty("提交数据")
	private String params;

	@ExcelProperty("执行时间")
	private Long time;

	@ExcelProperty("异常信息")
	private String exception;

	@ExcelProperty("应用标识")
	private String serviceId;

	@ExcelIgnore
	private String delFlag;

}
