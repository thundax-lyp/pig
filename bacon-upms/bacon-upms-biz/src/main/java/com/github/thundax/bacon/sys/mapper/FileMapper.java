package com.github.thundax.bacon.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.thundax.bacon.sys.entity.File;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统文件映射接口
 *
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {

}
