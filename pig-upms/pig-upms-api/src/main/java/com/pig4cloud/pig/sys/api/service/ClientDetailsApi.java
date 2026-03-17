package com.pig4cloud.pig.sys.api.service;

import com.pig4cloud.pig.sys.api.dto.SysOauthClientDetailsDTO;
import com.pig4cloud.pig.common.core.util.R;

/**
 * 客户端详情查询接口
 *
 * @author lengleng
 * @date 2026/03/16
 */
public interface ClientDetailsApi {

	R<SysOauthClientDetailsDTO> getClientDetailsById(String clientId);

}
