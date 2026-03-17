package com.pig4cloud.pig.sys.service.api;

import com.pig4cloud.pig.sys.api.service.ParamApi;
import com.pig4cloud.pig.sys.service.SysPublicParamService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 参数接口本地实现
 *
 * @author lengleng
 * @date 2026/03/16
 */
@Service
@RequiredArgsConstructor
public class ParamApiImpl implements ParamApi {

	private final SysPublicParamService sysPublicParamService;

	@Override
	public R<String> getByKey(String key) {
		return R.ok(sysPublicParamService.getParamValue(key));
	}

}
