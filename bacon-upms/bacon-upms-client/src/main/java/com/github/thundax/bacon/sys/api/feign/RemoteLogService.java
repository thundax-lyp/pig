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
