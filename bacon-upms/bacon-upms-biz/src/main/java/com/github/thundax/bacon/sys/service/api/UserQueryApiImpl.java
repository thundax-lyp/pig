package com.github.thundax.bacon.sys.service.api;

import com.github.thundax.bacon.sys.api.dto.UserDTO;
import com.github.thundax.bacon.sys.api.dto.UserInfo;
import com.github.thundax.bacon.sys.api.service.UserQueryApi;
import com.github.thundax.bacon.sys.service.UserService;
import com.github.thundax.bacon.common.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户查询接口本地实现
 *
 */
@Service
@RequiredArgsConstructor
public class UserQueryApiImpl implements UserQueryApi {

	private final UserService sysUserService;

	@Override
	public R<UserInfo> info(UserDTO user) {
		return sysUserService.getUserInfo(user);
	}

}
