package com.pig4cloud.pig.sys.api.service;

import com.pig4cloud.pig.sys.api.dto.UserDTO;
import com.pig4cloud.pig.sys.api.dto.UserInfo;
import com.pig4cloud.pig.common.core.util.R;

/**
 * 用户查询接口
 *
 * @author lengleng
 * @date 2026/03/16
 */
public interface UserQueryApi {

	R<UserInfo> info(UserDTO user);

}
