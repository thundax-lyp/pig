package com.pig4cloud.pig.sys.controller;

import com.pig4cloud.pig.sys.api.dto.RegisterUserDTO;
import com.pig4cloud.pig.sys.service.UserService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户注册控制器：提供用户注册功能
 *
 * @author lengleng
 * @date 2025/05/30
 */
@RestController
@RequestMapping("/sys/register")
@RequiredArgsConstructor
@Tag(name = "系统：注册用户管理模块", description = "提供系统注册用户的创建能力")
@ConditionalOnProperty(name = "register.user", matchIfMissing = true)
public class RegisterController {

	private final UserService userService;

	/**
	 * 注册用户
	 * @param registerUserDTO 注册用户信息DTO
	 * @return 注册结果封装对象
	 */
	@Inner(value = false)
	@SysLog("注册用户")
	@PostMapping("/user")
	@Operation(summary = "注册用户", description = "用于注册用户")
	public R<Boolean> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
		return userService.registerUser(registerUserDTO);
	}

}
