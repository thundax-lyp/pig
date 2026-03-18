
package com.github.thundax.bacon.common.core.constant.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 */
@Getter
@RequiredArgsConstructor
public enum LoginTypeEnum {

	/**
	 * 账号密码登录
	 */
	PWD("PWD", "账号密码登录"),

	/**
	 * 验证码登录
	 */
	SMS("SMS", "验证码登录");

	/**
	 * 类型
	 */
	private final String type;

	/**
	 * 描述
	 */
	private final String description;

}
