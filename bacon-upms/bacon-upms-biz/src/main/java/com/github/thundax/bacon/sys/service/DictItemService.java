package com.github.thundax.bacon.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.thundax.bacon.sys.entity.DictItem;
import com.github.thundax.bacon.common.core.util.R;

/**
 * 字典项服务接口
 *
 */
public interface DictItemService extends IService<DictItem> {

	/**
	 * 删除字典项
	 * @param id 字典项ID
	 * @return 操作结果
	 */
	R<?> removeDictItem(Long id);

	/**
	 * 更新字典项
	 * @param item 需要更新的字典项
	 * @return 操作结果
	 */
	R<?> updateDictItem(DictItem item);

}
