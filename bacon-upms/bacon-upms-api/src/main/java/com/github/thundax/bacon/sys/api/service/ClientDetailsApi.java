package com.github.thundax.bacon.sys.api.service;

import com.github.thundax.bacon.sys.api.dto.OauthClientDetailsDTO;
import com.github.thundax.bacon.common.core.util.R;

/**
 * 客户端详情查询接口
 *
 */
public interface ClientDetailsApi {

	R<OauthClientDetailsDTO> getClientDetailsById(String clientId);

}
