package com.github.thundax.bacon.codegen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.thundax.bacon.codegen.entity.GenGroupEntity;
import com.github.thundax.bacon.codegen.util.vo.GroupVO;
import com.github.thundax.bacon.codegen.util.vo.TemplateGroupDTO;

/**
 * 模板分组服务接口
 *
 */
public interface GenGroupService extends IService<GenGroupEntity> {

	/**
	 * 保存生成模板组
	 * @param genTemplateGroup 模板组DTO对象
	 */
	void saveGenGroup(TemplateGroupDTO genTemplateGroup);

	/**
	 * 删除分组极其关系
	 * @param ids ids
	 */
	void delGroupAndTemplate(Long[] ids);

	/**
	 * 查询group数据
	 * @param id id
	 */
	GroupVO getGroupVoById(Long id);

	/**
	 * 更新group数据
	 * @param GroupVo group
	 */
	void updateGroupAndTemplateById(GroupVO GroupVo);

}
