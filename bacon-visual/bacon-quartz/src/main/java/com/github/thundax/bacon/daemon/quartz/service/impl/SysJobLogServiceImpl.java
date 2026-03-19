package com.github.thundax.bacon.daemon.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.thundax.bacon.daemon.quartz.entity.SysJobLog;
import com.github.thundax.bacon.daemon.quartz.mapper.SysJobLogMapper;
import com.github.thundax.bacon.daemon.quartz.service.SysJobLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 定时任务执行日志服务实现类
 *
 */
@Service
@AllArgsConstructor
public class SysJobLogServiceImpl extends ServiceImpl<SysJobLogMapper, SysJobLog> implements SysJobLogService {

}
