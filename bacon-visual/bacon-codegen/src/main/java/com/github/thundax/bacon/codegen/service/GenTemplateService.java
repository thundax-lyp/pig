package com.github.thundax.bacon.codegen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.thundax.bacon.codegen.entity.GenTemplateEntity;
import com.github.thundax.bacon.common.core.util.R;

/**
 * 代码生成模板服务接口
 *
 */
public interface GenTemplateService extends IService<GenTemplateEntity> {

	/**
	 * 检查版本信息
	 * @return 返回检查结果，包含版本信息
	 */
	R<?> checkVersion();

	/**
	 * 在线更新
	 * @return 更新结果
	 */
	R<?> onlineUpdate();

}
