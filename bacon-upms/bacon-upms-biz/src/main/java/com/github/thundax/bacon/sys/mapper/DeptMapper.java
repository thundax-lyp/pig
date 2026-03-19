package com.github.thundax.bacon.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.thundax.bacon.sys.entity.Dept;
import com.github.thundax.bacon.sys.api.vo.DeptVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门管理 Mapper 接口
 *
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
