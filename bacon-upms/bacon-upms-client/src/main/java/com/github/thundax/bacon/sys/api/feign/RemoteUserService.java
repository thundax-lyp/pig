package com.github.thundax.bacon.sys.api.feign;

import com.github.thundax.bacon.sys.api.dto.UserDTO;
import com.github.thundax.bacon.sys.api.dto.UserInfo;
import com.github.thundax.bacon.common.core.constant.ServiceNameConstants;
import com.github.thundax.bacon.common.core.util.R;
import com.github.thundax.bacon.common.feign.annotation.NoToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 远程用户服务接口：提供用户信息查询功能
 *
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteUserService {

	/**
	 * 未登录状态下通过用户名查询用户和角色信息
	 * @param user 用户查询对象
	 * @return 用户信息
	 */
	@NoToken
	@GetMapping("/sys/user/info/query")
	R<UserInfo> info(@SpringQueryMap UserDTO user);

}
