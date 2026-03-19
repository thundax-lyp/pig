package com.github.thundax.bacon.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.thundax.bacon.sys.api.dto.LogDTO;
import com.github.thundax.bacon.sys.entity.Log;

import java.util.List;

/**
 * <p>
 * 日志表 服务类
 * </p>
 *
 * @since 2017-11-20
 */
public interface LogService extends IService<Log> {

	/**
	 * 分页查询系统日志
	 * @param page 分页对象
	 * @param sysLog 系统日志
	 * @return 系统日志分页数据
	 */
	Page getLogPage(Page page, LogDTO sysLog);

	/**
	 * 保存日志
	 * @param sysLog 日志实体
	 * @return Boolean
	 */
	Boolean saveLog(Log sysLog);

	/**
	 * 查询日志列表
	 * @param sysLog 查询条件
	 * @return 日志列表
	 */
	List<Log> listLogs(LogDTO sysLog);

}
