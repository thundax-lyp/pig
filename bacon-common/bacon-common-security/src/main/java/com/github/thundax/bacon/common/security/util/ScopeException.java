package com.github.thundax.bacon.common.security.util;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

import java.io.Serial;

/**
 * ScopeException 异常类，用于处理OAuth2认证过程中的作用域异常
 *
 */
public class ScopeException extends OAuth2AuthenticationException {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 使用指定消息构造ScopeException
	 * @param msg 详细消息
	 */
	public ScopeException(String msg) {
		super(new OAuth2Error(msg), msg);
	}

	/**
	 * 使用指定的错误信息和根异常构造ScopeException
	 * @param msg 错误详细信息
	 * @param cause 根异常
	 */
	public ScopeException(String msg, Throwable cause) {
		super(new OAuth2Error(msg), cause);
	}

}
