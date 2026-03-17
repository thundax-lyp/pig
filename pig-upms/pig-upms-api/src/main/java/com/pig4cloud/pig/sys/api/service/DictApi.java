package com.pig4cloud.pig.sys.api.service;

import com.pig4cloud.pig.sys.api.dto.SysDictItemDTO;
import com.pig4cloud.pig.common.core.util.R;

import java.util.List;

/**
 * 字典查询接口
 *
 * @author lengleng
 * @date 2026/03/16
 */
public interface DictApi {

	R<List<SysDictItemDTO>> getDictByType(String type);

}
