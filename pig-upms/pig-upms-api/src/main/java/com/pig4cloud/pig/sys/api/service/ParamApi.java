package com.pig4cloud.pig.sys.api.service;

import com.pig4cloud.pig.common.core.util.R;

/**
 * 参数查询接口
 *
 * @author lengleng
 * @date 2026/03/16
 */
public interface ParamApi {

	R<String> getByKey(String key);

}
