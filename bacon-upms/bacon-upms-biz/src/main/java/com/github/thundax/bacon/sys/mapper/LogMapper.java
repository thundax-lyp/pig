package com.github.thundax.bacon.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.thundax.bacon.sys.entity.Log;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志表 Mapper 接口
 *
 */
@Mapper
public interface LogMapper extends BaseMapper<Log> {

}
