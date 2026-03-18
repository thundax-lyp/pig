package com.github.thundax.bacon.sys.api.service;

import com.github.thundax.bacon.sys.api.dto.UserDTO;
import com.github.thundax.bacon.sys.api.dto.UserInfo;
import com.github.thundax.bacon.common.core.util.R;

/**
 * 用户查询接口
 *
 */
public interface UserQueryApi {

	R<UserInfo> info(UserDTO user);

}
