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

package com.pig4cloud.pig.sys.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户传输对象
 *
 * @author lengleng
 * @date 2017/11/5
 */
@Data
@Schema(description = "用户传输对象")
public class UserDTO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "主键ID")
	private Long userId;

	@Schema(description = "用户名")
	private String username;

	@Schema(description = "密码")
	private String password;

	@Schema(description = "随机盐")
	@JsonIgnore
	private String salt;

	@Schema(description = "创建人")
	private String createBy;

	@Schema(description = "修改人")
	private String updateBy;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "修改时间")
	private LocalDateTime updateTime;

	@Schema(description = "删除标记，1：已删除，0：正常")
	private String delFlag;

	@Schema(description = "锁定标记，0：正常，9：已锁定")
	private String lockFlag;

	@Schema(description = "手机号")
	private String phone;

	@Schema(description = "头像")
	private String avatar;

	/**
	 * 角色ID
	 */
	@Schema(description = "角色ID集合")
	private List<Long> role;

	/**
	 * 部门ID
	 */
	@Schema(description = "部门ID")
	private Long deptId;

	@Schema(description = "微信 OpenID")
	private String wxOpenid;

	@Schema(description = "微信小程序 OpenID")
	private String miniOpenid;

	@Schema(description = "QQ OpenID")
	private String qqOpenid;

	@Schema(description = "码云唯一标识")
	private String giteeLogin;

	@Schema(description = "开源中国唯一标识")
	private String oscId;

	@Schema(description = "昵称")
	private String nickname;

	@Schema(description = "姓名")
	private String name;

	@Schema(description = "邮箱")
	private String email;

	/**
	 * 岗位ID
	 */
	private List<Long> post;

	/**
	 * 新密码
	 */
	@Schema(description = "新密码")
	private String newpassword1;

}
