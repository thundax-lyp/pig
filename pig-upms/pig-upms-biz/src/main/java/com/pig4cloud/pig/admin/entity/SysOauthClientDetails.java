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

package com.pig4cloud.pig.admin.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * <p>
 * 客户端信息
 * </p>
 *
 * @author lengleng
 * @since 2018-05-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOauthClientDetails extends Model<SysOauthClientDetails> {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	private String clientId;

	private String clientSecret;

	private String resourceIds;

	private String scope;

	private String[] authorizedGrantTypes;

	private String webServerRedirectUri;

	private String authorities;

	private Integer accessTokenValidity;

	private Integer refreshTokenValidity;

	private String additionalInformation;

	private String autoapprove;

	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	private String delFlag;

	@TableField(fill = FieldFill.INSERT)
	private String createBy;

	@TableField(fill = FieldFill.UPDATE)
	private String updateBy;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;

}
