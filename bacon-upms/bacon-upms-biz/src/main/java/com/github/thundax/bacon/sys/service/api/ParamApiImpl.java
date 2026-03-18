package com.github.thundax.bacon.sys.service.api;

import com.github.thundax.bacon.sys.api.service.ParamApi;
import com.github.thundax.bacon.sys.service.PublicParamService;
import com.github.thundax.bacon.common.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 参数接口本地实现
 *
 */
@Service
@RequiredArgsConstructor
public class ParamApiImpl implements ParamApi {

	private final PublicParamService sysPublicParamService;

	@Override
	public R<String> getByKey(String key) {
		return R.ok(sysPublicParamService.getParamValue(key));
	}

}
