package com.github.thundax.bacon.sys.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.thundax.bacon.sys.api.dto.LogDTO;
import com.github.thundax.bacon.sys.entity.Log;
import com.github.thundax.bacon.sys.mapper.LogMapper;
import com.github.thundax.bacon.sys.service.LogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统日志服务实现类
 *
 * @since 2017-11-20
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

	/**
	 * 分页查询系统日志
	 * @param page 分页参数
	 * @param sysLog 日志查询条件
	 * @return 分页结果
	 */
	@Override
	public Page getLogPage(Page page, LogDTO sysLog) {
		return baseMapper.selectPage(page, buildQuery(sysLog));
	}

	/**
	 * 保存日志
	 * @param sysLog 日志对象
	 * @return 保存成功返回true
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveLog(Log sysLog) {
		baseMapper.insert(sysLog);
		return Boolean.TRUE;
	}

	/**
	 * 查询日志列表
	 * @param sysLog 查询条件DTO对象
	 * @return 日志列表
	 */
	@Override
	public List<Log> listLogs(LogDTO sysLog) {
		return baseMapper.selectList(buildQuery(sysLog));
	}

	/**
	 * 构建查询条件
	 * @param sysLog 前端查询条件DTO
	 * @return 构建好的LambdaQueryWrapper对象
	 */
	private LambdaQueryWrapper buildQuery(LogDTO sysLog) {
		LambdaQueryWrapper<Log> wrapper = Wrappers.lambdaQuery();
		if (StrUtil.isNotBlank(sysLog.getLogType())) {
			wrapper.eq(Log::getLogType, sysLog.getLogType());
		}

		if (ArrayUtil.isNotEmpty(sysLog.getCreateTime())) {
			wrapper.ge(Log::getCreateTime, sysLog.getCreateTime()[0]).le(Log::getCreateTime, sysLog.getCreateTime()[1]);
		}

		return wrapper;
	}

}
