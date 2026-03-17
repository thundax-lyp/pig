package com.pig4cloud.pig.admin.service.api;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pig.admin.api.dto.SysOauthClientDetailsDTO;
import com.pig4cloud.pig.admin.api.entity.SysOauthClientDetails;
import com.pig4cloud.pig.admin.api.service.ClientDetailsApi;
import com.pig4cloud.pig.admin.service.SysOauthClientDetailsService;
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

	private final SysOauthClientDetailsService clientDetailsService;

	@Override
	public R<SysOauthClientDetailsDTO> getClientDetailsById(String clientId) {
		SysOauthClientDetails clientDetails = clientDetailsService.getOne(
				Wrappers.<SysOauthClientDetails>lambdaQuery().eq(SysOauthClientDetails::getClientId, clientId), false);
		return R.ok(BeanUtil.copyProperties(clientDetails, SysOauthClientDetailsDTO.class));
	}

}
