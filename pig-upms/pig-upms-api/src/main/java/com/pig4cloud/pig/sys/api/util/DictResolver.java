package com.pig4cloud.pig.sys.api.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.pig4cloud.pig.sys.api.dto.SysDictItemDTO;
import com.pig4cloud.pig.sys.api.service.DictApi;
import com.pig4cloud.pig.common.core.util.SpringContextHolder;
import lombok.experimental.UtilityClass;

import java.util.List;

/**
 * 字典解析工具类：提供字典数据的查询和解析功能
 *
 * @author lengleng
 * @date 2025/05/30
 */
@UtilityClass
public class DictResolver {

	/**
	 * 根据字典类型获取所有字典项
	 * @param type 字典类型
	 * @return 字典数据项集合
	 */
	public List<SysDictItemDTO> getDictItemsByType(String type) {
		Assert.isTrue(StrUtil.isNotBlank(type), "参数不合法");

		DictApi dictApi = SpringContextHolder.getBean(DictApi.class);

		return dictApi.getDictByType(type).getData();
	}

	/**
	 * 根据字典类型以及字典项字典值获取字典标签
	 * @param type 字典类型
	 * @param itemValue 字典项字典值
	 * @return 字典项标签值
	 */
	public String getDictItemLabel(String type, String itemValue) {
		Assert.isTrue(StrUtil.isNotBlank(type) && StrUtil.isNotBlank(itemValue), "参数不合法");

		SysDictItemDTO sysDictItem = getDictItemByItemValue(type, itemValue);

		return ObjectUtil.isNotEmpty(sysDictItem) ? sysDictItem.getLabel() : StrUtil.EMPTY;
	}

	/**
	 * 根据字典类型以及字典标签获取字典值
	 * @param type 字典类型
	 * @param itemLabel 字典数据标签
	 * @return 字典数据项值
	 */
	public String getDictItemValue(String type, String itemLabel) {
		Assert.isTrue(StrUtil.isNotBlank(type) && StrUtil.isNotBlank(itemLabel), "参数不合法");

		SysDictItemDTO sysDictItem = getDictItemByItemLabel(type, itemLabel);

		return ObjectUtil.isNotEmpty(sysDictItem) ? sysDictItem.getItemValue() : StrUtil.EMPTY;
	}

	/**
	 * 根据字典类型以及字典值获取字典项
	 * @param type 字典类型
	 * @param itemValue 字典数据值
	 * @return 字典数据项
	 */
	public SysDictItemDTO getDictItemByItemValue(String type, String itemValue) {
		Assert.isTrue(StrUtil.isNotBlank(type) && StrUtil.isNotBlank(itemValue), "参数不合法");

		List<SysDictItemDTO> dictItemList = getDictItemsByType(type);

		if (CollUtil.isNotEmpty(dictItemList)) {
			return dictItemList.stream().filter(item -> itemValue.equals(item.getItemValue())).findFirst().orElse(null);
		}

		return null;
	}

	/**
	 * 根据字典类型以及字典标签获取字典项
	 * @param type 字典类型
	 * @param itemLabel 字典数据项标签
	 * @return 字典数据项
	 */
	public SysDictItemDTO getDictItemByItemLabel(String type, String itemLabel) {
		Assert.isTrue(StrUtil.isNotBlank(type) && StrUtil.isNotBlank(itemLabel), "参数不合法");

		List<SysDictItemDTO> dictItemList = getDictItemsByType(type);

		if (CollUtil.isNotEmpty(dictItemList)) {
			return dictItemList.stream().filter(item -> itemLabel.equals(item.getLabel())).findFirst().orElse(null);
		}

		return null;
	}

}
