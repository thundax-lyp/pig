/*
 *
 *      Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */
package com.pig4cloud.pig.sys.entity;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author lengleng
 * @since 2017-11-20
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@FieldNameConstants
public class Log implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.ASSIGN_ID)
	@ExcelProperty("日志编号")
	private Long id;

	@ExcelProperty("日志类型（0-正常 9-错误）")
	private String logType;

	@ExcelProperty("日志标题")
	private String title;

	@ExcelProperty("创建人")
	@TableField(fill = FieldFill.INSERT)
	private String createBy;

	@ExcelProperty("创建时间")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@ExcelIgnore
	@TableField(fill = FieldFill.UPDATE)
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

	@TableLogic
	@ExcelIgnore
	@TableField(fill = FieldFill.INSERT)
	private String delFlag;

}
