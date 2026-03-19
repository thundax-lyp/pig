package com.github.thundax.bacon.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.thundax.bacon.sys.entity.DictItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统字典项数据访问接口
 *
 */
@Mapper
public interface DictItemMapper extends BaseMapper<DictItem> {

}
