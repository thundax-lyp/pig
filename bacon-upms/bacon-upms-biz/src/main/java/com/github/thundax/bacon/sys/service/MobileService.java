package com.github.thundax.bacon.sys.service;

import com.github.thundax.bacon.common.core.util.R;

/**
 * 系统手机服务接口：提供手机验证码发送功能
 *
 */
public interface MobileService {

	/**
	 * 发送手机验证码
	 * @param mobile 手机号码
	 * @return 发送结果，成功返回true，失败返回false
	 */
	R<Boolean> sendSmsCode(String mobile);

}
