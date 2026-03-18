package com.pig4cloud.pig.sys.service.api;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pig.sys.api.dto.OauthClientDetailsDTO;
import com.pig4cloud.pig.sys.entity.OauthClientDetails;
import com.pig4cloud.pig.sys.api.service.ClientDetailsApi;
import com.pig4cloud.pig.sys.service.OauthClientDetailsService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 客户端详情接口本地实现
 *
 * @author lengleng
 * @date 2026/03/16
 */
@Service
@RequiredArgsConstructor
public class ClientDetailsApiImpl implements ClientDetailsApi {

	private final OauthClientDetailsService clientDetailsService;

	@Override
	public R<OauthClientDetailsDTO> getClientDetailsById(String clientId) {
		OauthClientDetails clientDetails = clientDetailsService.getOne(
				Wrappers.<OauthClientDetails>lambdaQuery().eq(OauthClientDetails::getClientId, clientId), false);
		return R.ok(BeanUtil.copyProperties(clientDetails, OauthClientDetailsDTO.class));
	}

}
