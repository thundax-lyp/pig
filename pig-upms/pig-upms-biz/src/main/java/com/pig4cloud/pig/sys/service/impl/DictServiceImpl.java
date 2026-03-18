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
package com.pig4cloud.pig.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.sys.entity.Dict;
import com.pig4cloud.pig.sys.entity.DictItem;
import com.pig4cloud.pig.sys.mapper.DictItemMapper;
import com.pig4cloud.pig.sys.mapper.DictMapper;
import com.pig4cloud.pig.sys.service.DictService;
import com.pig4cloud.pig.common.core.constant.CacheConstants;
import com.pig4cloud.pig.common.core.constant.enums.DictTypeEnum;
import com.pig4cloud.pig.common.core.exception.ErrorCodes;
import com.pig4cloud.pig.common.core.support.JetCacheVersionSupport;
import com.pig4cloud.pig.common.core.util.MsgUtils;
import com.pig4cloud.pig.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统字典服务实现类
 *
 * @author lengleng
 * @date 2025/05/30
 */
@Service
@AllArgsConstructor
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

	private final DictItemMapper dictItemMapper;

	private final JetCacheVersionSupport jetCacheVersionSupport;

	/**
	 * 根据ID删除字典
	 * @param ids 字典ID数组
	 * @return 操作结果
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public R removeDictByIds(Long[] ids) {

		List<Long> dictIdList = baseMapper.selectByIds(CollUtil.toList(ids))
			.stream()
			.filter(sysDict -> !sysDict.getSystemFlag().equals(DictTypeEnum.SYSTEM.getType()))// 系统内置类型不删除
			.map(Dict::getId)
			.toList();

		baseMapper.deleteByIds(dictIdList);

		dictItemMapper.delete(Wrappers.<DictItem>lambdaQuery().in(DictItem::getDictId, dictIdList));
		jetCacheVersionSupport.increment(CacheConstants.DICT_DETAILS);
		return R.ok();
	}

	/**
	 * 更新字典数据
	 * @param dict 字典对象
	 * @return 操作结果
	 * @see R 返回结果封装类
	 */
	@Override
	@CacheInvalidate(name = CacheConstants.DICT_DETAILS + ":",
			key = "@jetCacheVersionSupport.versionedKey('" + CacheConstants.DICT_DETAILS + "', #dict.dictType)")
	public R updateDict(Dict dict) {
		Dict sysDict = this.getById(dict.getId());
		// 系统内置
		if (DictTypeEnum.SYSTEM.getType().equals(sysDict.getSystemFlag())) {
			return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_DICT_UPDATE_SYSTEM));
		}
		this.updateById(dict);
		return R.ok(dict);
	}

	/**
	 * 同步字典缓存（清空缓存）
	 * @return 操作结果
	 */
	@Override
	public R syncDictCache() {
		jetCacheVersionSupport.increment(CacheConstants.DICT_DETAILS);
		return R.ok();
	}

}
