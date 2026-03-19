package com.github.thundax.bacon.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.thundax.bacon.sys.entity.PublicParam;
import com.github.thundax.bacon.sys.mapper.PublicParamMapper;
import com.github.thundax.bacon.sys.service.PublicParamService;
import com.github.thundax.bacon.common.core.constant.CacheConstants;
import com.github.thundax.bacon.common.core.constant.enums.DictTypeEnum;
import com.github.thundax.bacon.common.core.exception.ErrorCodes;
import com.github.thundax.bacon.common.core.support.JetCacheVersionSupport;
import com.github.thundax.bacon.common.core.util.MsgUtils;
import com.github.thundax.bacon.common.core.util.R;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 系统公共参数服务实现类
 *
 */
@Service
@AllArgsConstructor
public class PublicParamServiceImpl extends ServiceImpl<PublicParamMapper, PublicParam> implements PublicParamService {

	private final JetCacheVersionSupport jetCacheVersionSupport;

	/**
	 * 根据公共参数key获取对应的value值
	 * @param publicKey 公共参数key
	 * @return 公共参数value，未找到时返回null
	 * @Cacheable 使用缓存，缓存名称为PARAMS_DETAILS，key为publicKey，当结果为null时不缓存
	 */
	@Override
	@Cached(name = CacheConstants.PARAMS_DETAILS + ":",
			key = "@jetCacheVersionSupport.versionedKey('" + CacheConstants.PARAMS_DETAILS + "', #publicKey)",
			expire = 1, timeUnit = TimeUnit.HOURS, cacheType = CacheType.REMOTE, postCondition = "#result != null")
	public String getParamValue(String publicKey) {
		PublicParam sysPublicParam = this.baseMapper
			.selectOne(Wrappers.<PublicParam>lambdaQuery().eq(PublicParam::getPublicKey, publicKey));

		if (sysPublicParam != null) {
			return sysPublicParam.getPublicValue();
		}
		return null;
	}

	/**
	 * 更新系统公共参数
	 * @param sysPublicParam 系统公共参数对象
	 * @return 操作结果
	 * @see R
	 */
	@Override
	@CacheInvalidate(name = CacheConstants.PARAMS_DETAILS + ":", key = "@jetCacheVersionSupport.versionedKey('"
			+ CacheConstants.PARAMS_DETAILS + "', #sysPublicParam.publicKey)")
	public R updateParam(PublicParam sysPublicParam) {
		PublicParam param = this.getById(sysPublicParam.getPublicId());
		// 系统内置
		if (DictTypeEnum.SYSTEM.getType().equals(param.getSystemFlag())) {
			return R.failed(MsgUtils.getMessage(ErrorCodes.SYS_PARAM_DELETE_SYSTEM));
		}
		return R.ok(this.updateById(sysPublicParam));
	}

	/**
	 * 根据ID列表删除参数
	 * @param publicIds 参数ID数组
	 * @return 操作结果
	 * @see CacheConstants#PARAMS_DETAILS 缓存常量
	 */
	@Override
	public R removeParamByIds(Long[] publicIds) {
		List<Long> idList = this.baseMapper.selectByIds(CollUtil.toList(publicIds))
			.stream()
			.filter(p -> !p.getSystemFlag().equals(DictTypeEnum.SYSTEM.getType()))// 系统内置的跳过不能删除
			.map(PublicParam::getPublicId)
			.toList();
		R result = R.ok(this.removeBatchByIds(idList));
		jetCacheVersionSupport.increment(CacheConstants.PARAMS_DETAILS);
		return result;
	}

	/**
	 * 同步参数缓存
	 * @return 操作结果
	 */
	@Override
	public R syncParamCache() {
		jetCacheVersionSupport.increment(CacheConstants.PARAMS_DETAILS);
		return R.ok();
	}

}
