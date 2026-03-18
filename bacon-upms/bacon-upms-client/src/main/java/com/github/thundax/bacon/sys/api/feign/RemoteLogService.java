/*
 *
 *      Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */

package com.github.thundax.bacon.sys.api.feign;

import com.github.thundax.bacon.sys.api.dto.LogRecordDTO;
import com.github.thundax.bacon.common.core.constant.ServiceNameConstants;
import com.github.thundax.bacon.common.core.util.R;
import com.github.thundax.bacon.common.feign.annotation.NoToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 远程日志服务接口
 *
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteLogService {

	/**
	 * 保存日志
	 * @param sysLog 日志记录对象
	 * @return 保存结果
	 */
	@NoToken
	@PostMapping("/sys/log/save")
	R<Boolean> saveLog(@RequestBody LogRecordDTO sysLog);

}
