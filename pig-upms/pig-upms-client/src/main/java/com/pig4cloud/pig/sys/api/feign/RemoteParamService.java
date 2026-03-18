package com.pig4cloud.pig.sys.api.feign;

import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.feign.annotation.NoToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 远程参数服务接口
 * <p>
 * 通过Feign客户端调用UPMS服务获取参数配置
 * </p>
 *
 * @author lengleng
 * @date 2025/05/30
 * @see FeignClient
 */
@FeignClient(contextId = "remoteParamService", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteParamService {

	/**
	 * 通过键查询参数配置
	 * @param key 参数键
	 */
	@NoToken
	@GetMapping("/sys/param/publicValue/{key}")
	R<String> getByKey(@PathVariable String key);

}
