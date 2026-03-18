package com.pig4cloud.pig.sys.api.service;

import com.pig4cloud.pig.sys.api.dto.DictItemDTO;
import com.pig4cloud.pig.common.core.util.R;

import java.util.List;

/**
 * 字典查询接口
 *
 * @author lengleng
 * @date 2026/03/16
 */
public interface DictApi {

	R<List<DictItemDTO>> getDictByType(String type);

}
