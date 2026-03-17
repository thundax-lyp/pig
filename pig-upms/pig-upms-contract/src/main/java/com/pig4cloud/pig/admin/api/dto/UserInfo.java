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

package com.pig4cloud.pig.admin.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pig4cloud.pig.admin.api.vo.DeptVO;
import com.pig4cloud.pig.admin.api.vo.PostVO;
import com.pig4cloud.pig.admin.api.vo.RoleItemVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息实体类，继承自UserVO并实现Serializable接口 , spring security
 *
 * @author lengleng
 * @date 2025/06/28
 */
@Data
@Schema(description = "spring security 用户信息")
public class UserInfo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "主键")
	private Long userId;

	@Schema(description = "用户名")
	private String username;

	/**
	 * 密码
	 */
	@JsonIgnore(value = false)
	private String password;

	/**
	 * 随机盐
	 */
	@JsonIgnore(value = false)
	private String salt;

	@Schema(description = "微信open id")
	private String wxOpenid;

	@Schema(description = "qq open id")
	private String qqOpenid;

	@Schema(description = "gitee open id")
	private String giteeOpenId;

	@Schema(description = "开源中国 open id")
	private String oscOpenId;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "修改时间")
	private LocalDateTime updateTime;

	@Schema(description = "删除标记,1:已删除,0:正常")
	private String delFlag;

	@Schema(description = "锁定标记,0:正常,9:已锁定")
	private String lockFlag;

	@Schema(description = "手机号")
	private String phone;

	@Schema(description = "头像")
	private String avatar;

	@Schema(description = "所属部门")
	private DeptVO dept;

	@Schema(description = "拥有的角色列表")
	private List<RoleItemVO> roleList = new ArrayList<>();

	@Schema(description = "岗位列表")
	private List<PostVO> postList = new ArrayList<>();

	@Schema(description = "昵称")
	private String nickname;

	@Schema(description = "姓名")
	private String name;

	@Schema(description = "邮箱")
	private String email;

	/**
	 * 权限标识集合
	 */
	@Schema(description = "权限标识集合")
	private List<String> permissions = new ArrayList<>();

}
