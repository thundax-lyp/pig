package com.github.thundax.bacon.sys.service.api;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.thundax.bacon.sys.api.dto.OauthClientDetailsDTO;
import com.github.thundax.bacon.sys.entity.OauthClientDetails;
import com.github.thundax.bacon.sys.api.service.ClientDetailsApi;
import com.github.thundax.bacon.sys.service.OauthClientDetailsService;
import com.github.thundax.bacon.common.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 客户端详情接口本地实现
 *
 */
@Service
@RequiredArgsConstructor
public class ClientDetailsApiImpl implements ClientDetailsApi {

	private final OauthClientDetailsService clientDetailsService;

	@Override
	public R<OauthClientDetailsDTO> getClientDetailsById(String clientId) {
		OauthClientDetails clientDetails = clientDetailsService
			.getOne(Wrappers.<OauthClientDetails>lambdaQuery().eq(OauthClientDetails::getClientId, clientId), false);
		return R.ok(BeanUtil.copyProperties(clientDetails, OauthClientDetailsDTO.class));
	}

}
