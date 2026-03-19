package com.github.thundax.bacon.daemon.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.thundax.bacon.daemon.quartz.entity.SysJob;
import com.github.thundax.bacon.daemon.quartz.mapper.SysJobMapper;
import com.github.thundax.bacon.daemon.quartz.service.SysJobService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 定时任务调度服务实现类
 *
 */
@Service
@AllArgsConstructor
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements SysJobService {

}
