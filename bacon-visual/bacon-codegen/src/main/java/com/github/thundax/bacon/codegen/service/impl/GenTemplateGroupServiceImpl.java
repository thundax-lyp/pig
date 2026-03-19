package com.github.thundax.bacon.codegen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.thundax.bacon.codegen.entity.GenTemplateGroupEntity;
import com.github.thundax.bacon.codegen.mapper.GenTemplateGroupMapper;
import com.github.thundax.bacon.codegen.service.GenTemplateGroupService;
import org.springframework.stereotype.Service;

/**
 * 模板分组关联表服务实现类
 *
 */
@Service
public class GenTemplateGroupServiceImpl extends ServiceImpl<GenTemplateGroupMapper, GenTemplateGroupEntity>
		implements GenTemplateGroupService {

}
