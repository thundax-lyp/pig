package com.github.thundax.bacon.sys.api.service;

import com.github.thundax.bacon.sys.api.dto.DictItemDTO;
import com.github.thundax.bacon.common.core.util.R;

import java.util.List;

/**
 * 字典查询接口
 *
 */
public interface DictApi {

	R<List<DictItemDTO>> getDictByType(String type);

}
