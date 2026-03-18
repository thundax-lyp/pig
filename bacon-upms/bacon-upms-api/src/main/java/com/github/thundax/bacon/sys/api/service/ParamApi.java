package com.github.thundax.bacon.sys.api.service;

import com.github.thundax.bacon.common.core.util.R;

/**
 * 参数查询接口
 *
 */
public interface ParamApi {

	R<String> getByKey(String key);

}
