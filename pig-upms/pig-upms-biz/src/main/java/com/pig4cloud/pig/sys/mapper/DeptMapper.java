/*
 *
 *      Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */

package com.pig4cloud.pig.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pig.sys.entity.Dept;
import com.pig4cloud.pig.sys.api.vo.DeptVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门管理 Mapper 接口
 *
 * @author lengleng
 * @since 2018-01-20
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

	/**
	 * 查询轻量部门展示对象
	 * @param deptId 部门id
	 * @return 部门展示对象
	 */
	DeptVO getDeptVoById(Long deptId);

}
