/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.github.thundax.bacon.sys.service.impl;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.thundax.bacon.sys.entity.Dict;
import com.github.thundax.bacon.sys.entity.DictItem;
import com.github.thundax.bacon.sys.mapper.DictItemMapper;
import com.github.thundax.bacon.sys.service.DictItemService;
import com.github.thundax.bacon.sys.service.DictService;
import com.github.thundax.bacon.common.core.constant.CacheConstants;
import com.github.thundax.bacon.common.core.constant.enums.DictTypeEnum;
import com.github.thundax.bacon.common.core.exception.ErrorCodes;
import com.github.thundax.bacon.common.core.support.JetCacheVersionSupport;
import com.github.thundax.bacon.common.core.util.MsgUtils;
import com.github.thundax.bacon.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 字典项服务实现类
 *
 */
@Service
@AllArgsConstructor
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements DictItemService {

	private final DictService dictService;

	private final JetCacheVersionSupport jetCacheVersionSupport;

	/**
	 * 删除字典项
	 * @param id 字典项ID
	 * @return 操作结果
	 * @see R
	 */
	@Override
	public R removeDictItem(Long id) {
		// 根据ID查询字典ID
		DictItem dictItem = this.getById(id);
		Dict dict = dictService.getById(dictItem.getDictId());
		// 系统内置
		if (DictTypeEnum.SYSTEM.getType().equals(dict.getSystemFlag())) {
			return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_DICT_DELETE_SYSTEM));
		}
		R result = R.ok(this.removeById(id));
		jetCacheVersionSupport.increment(CacheConstants.DICT_DETAILS);
		return result;
	}

	/**
	 * 更新字典项
	 * @param item 需要更新的字典项
	 * @return 操作结果，包含成功或失败信息
	 * @see R
	 */
	@Override
	@CacheInvalidate(name = CacheConstants.DICT_DETAILS + ":",
			key = "@jetCacheVersionSupport.versionedKey('" + CacheConstants.DICT_DETAILS + "', #item.dictType)")
	public R updateDictItem(DictItem item) {
		// 查询字典
		Dict dict = dictService.getById(item.getDictId());
		// 系统内置
		if (DictTypeEnum.SYSTEM.getType().equals(dict.getSystemFlag())) {
			return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_DICT_UPDATE_SYSTEM));
		}
		return R.ok(this.updateById(item));
	}

}
