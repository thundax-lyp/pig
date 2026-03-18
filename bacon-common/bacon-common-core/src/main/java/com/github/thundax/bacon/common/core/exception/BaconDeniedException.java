
package com.github.thundax.bacon.common.core.exception;

import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 授权拒绝异常类
 *
 */
@NoArgsConstructor
public class BaconDeniedException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	public BaconDeniedException(String message) {
		super(message);
	}

	public BaconDeniedException(Throwable cause) {
		super(cause);
	}

	public BaconDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaconDeniedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
