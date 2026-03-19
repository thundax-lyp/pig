package com.github.thundax.bacon.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.thundax.bacon.sys.entity.OauthClientDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统OAuth客户端详情 Mapper接口
 *
 */
@Mapper
public interface OauthClientDetailsMapper extends BaseMapper<OauthClientDetails> {

}
