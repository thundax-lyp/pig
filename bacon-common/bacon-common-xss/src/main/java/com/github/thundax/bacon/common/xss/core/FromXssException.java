package com.github.thundax.bacon.common.xss.core;

import lombok.Getter;

import java.io.Serial;

/**
 * XSS 表单异常类，用于处理表单相关的 XSS 异常情况
 *
 * @see IllegalStateException
 * @see XssException
 */
@Getter
public class FromXssException extends IllegalStateException implements XssException {

	@Serial
	private static final long serialVersionUID = 1L;

	private final String input;

	/**
	 * 构造FromXssException异常
	 * @param input 引发异常的输入内容
	 * @param message 异常信息
	 */
	public FromXssException(String input, String message) {
		super(message);
		this.input = input;
	}

}
