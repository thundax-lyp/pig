/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.pig.sys.api.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.feign.annotation.NoToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 远程令牌服务接口
 *
 * @author lengleng
 * @date 2025/05/30
 */
@FeignClient(contextId = "remoteTokenService", value = ServiceNameConstants.AUTH_SERVICE)
public interface RemoteTokenService {

	/**
	 * 分页查询令牌
	 * @param params 分页参数
	 * @return 分页结果
	 */
	@NoToken
	@PostMapping("/sys/token/page")
	R<Page> getTokenPage(@RequestBody Map<String, Object> params);

	/**
	 * 根据令牌删除令牌
	 * @param token 要删除的令牌
	 * @return 删除操作结果，包含是否成功的布尔值
	 */
	@NoToken
	@DeleteMapping("/sys/token/remove/{token}")
	R<Boolean> removeTokenById(@PathVariable String token);

	/**
	 * 根据令牌查询用户信息
	 * @param token 用户令牌
	 * @return 包含用户信息的响应结果
	 */
	@NoToken
	@GetMapping("/sys/token/query-token")
	R<Map<String, Object>> queryToken(@RequestParam("token") String token);

}
