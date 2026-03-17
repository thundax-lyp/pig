package com.pig4cloud.pig.sys.service.api;

import com.pig4cloud.pig.sys.api.dto.UserDTO;
import com.pig4cloud.pig.sys.api.dto.UserInfo;
import com.pig4cloud.pig.sys.api.service.UserQueryApi;
import com.pig4cloud.pig.sys.service.SysUserService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户查询接口本地实现
 *
 * @author lengleng
 * @date 2026/03/16
 */
@Service
@RequiredArgsConstructor
public class UserQueryApiImpl implements UserQueryApi {

	private final SysUserService sysUserService;

	@Override
	public R<UserInfo> info(UserDTO user) {
		return sysUserService.getUserInfo(user);
	}

}
