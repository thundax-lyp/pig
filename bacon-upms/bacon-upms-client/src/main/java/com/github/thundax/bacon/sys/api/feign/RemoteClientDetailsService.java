package com.github.thundax.bacon.sys.api.feign;

import com.github.thundax.bacon.sys.api.dto.OauthClientDetailsDTO;
import com.github.thundax.bacon.common.core.constant.ServiceNameConstants;
import com.github.thundax.bacon.common.core.util.R;
import com.github.thundax.bacon.common.feign.annotation.NoToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 远程客户端详情服务接口
 *
 */
@FeignClient(contextId = "remoteClientDetailsService", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteClientDetailsService {

	/**
	 * 通过客户端ID查询客户端信息
	 * @param clientId 客户端ID
	 * @return 客户端信息
	 */
	@NoToken
	@GetMapping("/sys/client/getClientDetailsById/{clientId}")
	R<OauthClientDetailsDTO> getClientDetailsById(@PathVariable String clientId);

}
