
package com.github.thundax.bacon.common.security.service;

import com.github.thundax.bacon.sys.api.dto.UserDTO;
import com.github.thundax.bacon.sys.api.dto.UserInfo;
import com.github.thundax.bacon.sys.api.service.UserQueryApi;
import com.github.thundax.bacon.common.core.util.R;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户详情服务实现类，提供基于用户名加载用户详情功能
 *
 */
@Primary
@RequiredArgsConstructor
public class BaconUserDetailsServiceImpl implements BaconUserDetailsService {

	private final UserQueryApi userQueryApi;

	private final UserDetailsCacheService userDetailsCacheService;

	/**
	 * 根据用户名加载用户详情
	 * @param username 用户名
	 * @return 用户详情信息
	 */
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String username) {
		UserDetails userDetails = userDetailsCacheService.get(username);
		if (userDetails != null) {
			return userDetails;
		}

		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(username);
		R<UserInfo> result = userQueryApi.info(userDTO);
		userDetails = getUserDetails(result);
		userDetailsCacheService.put(username, userDetails);
		return userDetails;
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
