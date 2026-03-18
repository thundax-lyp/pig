
package com.github.thundax.bacon.common.core.exception;

import java.io.Serial;

/**
 * 验证码异常类
 *
 */
public class ValidateCodeException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = -7285211528095468156L;

	public ValidateCodeException() {
	}

	public ValidateCodeException(String msg) {
		super(msg);
	}

}
