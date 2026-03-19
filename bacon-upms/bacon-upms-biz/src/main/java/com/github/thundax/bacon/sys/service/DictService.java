package com.github.thundax.bacon.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.thundax.bacon.sys.entity.Dict;
import com.github.thundax.bacon.common.core.util.R;

/**
 * 字典表服务接口 提供字典数据的增删改查及缓存同步功能
 *
 */
public interface DictService extends IService<Dict> {

	/**
	 * 根据ID列表删除字典
	 * @param ids 要删除的字典ID数组
	 * @return 操作结果
	 */
	R<?> removeDictByIds(Long[] ids);

	/**
	 * 更新字典
	 * @param sysDict 要更新的字典对象
	 * @return 操作结果
	 */
	R<?> updateDict(Dict sysDict);

	/**
	 * 同步字典缓存（清空缓存）
	 * @return 操作结果
	 */
	R<?> syncDictCache();

}
