package com.github.thundax.bacon.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.thundax.bacon.sys.entity.PublicParam;
import com.github.thundax.bacon.common.core.util.R;

/**
 * 系统公共参数配置表 服务类
 *
 */
public interface PublicParamService extends IService<PublicParam> {

	/**
	 * 根据公共参数key获取对应的value值
	 * @param publicKey 公共参数key
	 * @return 公共参数value，未找到时返回null
	 */
	String getParamValue(String publicKey);

	/**
	 * 更新系统公共参数
	 * @param sysPublicParam 系统公共参数对象
	 * @return 操作结果
	 */
	R<?> updateParam(PublicParam sysPublicParam);

	/**
	 * 根据ID删除参数
	 * @param publicIds 参数ID数组
	 * @return 删除结果
	 */
	R<?> removeParamByIds(Long[] publicIds);

	/**
	 * 同步参数缓存
	 * @return 操作结果
	 */
	R<?> syncParamCache();

}
