package com.github.thundax.bacon.sys.controller;

import com.github.thundax.bacon.sys.service.MobileService;
import com.github.thundax.bacon.common.core.util.R;
import com.github.thundax.bacon.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 手机管理模块控制器：提供手机验证码相关服务
 *
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys/mobile")
@Tag(name = "系统：手机管理模块", description = "提供系统短信验证码发送与校验能力")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class MobileController {

	private final MobileService mobileService;

	/**
	 * 发送短信验证码
	 * @param mobile 手机号码
	 * @return 操作结果
	 */
	@Inner(value = false)
	@GetMapping("/{mobile}")
	@Operation(summary = "发送短信验证码", description = "用于发送短信验证码")
	public R sendSmsCode(@PathVariable String mobile) {
		return mobileService.sendSmsCode(mobile);
	}

}
