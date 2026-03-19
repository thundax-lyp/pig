package com.github.thundax.bacon.codegen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.thundax.bacon.codegen.entity.GenDatasourceConf;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源表 Mapper 接口
 *
 */
@Mapper
public interface GenDatasourceConfMapper extends BaseMapper<GenDatasourceConf> {

}
