package com.github.thundax.bacon.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @since 2017-10-29
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@FieldNameConstants
public class User implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "user_id", type = IdType.ASSIGN_ID)
	private Long userId;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 随机盐
	 */
	private String salt;

	/**
	 * 创建人
	 */
	@TableField(fill = FieldFill.INSERT)
	private String createBy;

	/**
	 * 修改人
	 */
	@TableField(fill = FieldFill.UPDATE)
	private String updateBy;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;

	/**
	 * 0-正常，1-删除
	 */
	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	private String delFlag;

	/**
	 * 锁定标记
	 */
	private String lockFlag;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 部门ID
	 */
	private Long deptId;

	/**
	 * 微信openid
	 */
	private String wxOpenid;

	/**
	 * 微信小程序openId
	 */
	private String miniOpenid;

	/**
	 * QQ openid
	 */
	private String qqOpenid;

	/**
	 * 码云唯一标识
	 */
	private String giteeLogin;

	/**
	 * 开源中国唯一标识
	 */
	private String oscId;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 邮箱
	 */
	private String email;

}
