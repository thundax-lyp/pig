package com.github.thundax.bacon.codegen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.thundax.bacon.codegen.entity.GenFieldType;

import java.util.Set;

/**
 * 列属性服务接口
 *
 */
public interface GenFieldTypeService extends IService<GenFieldType> {

	/**
	 * 根据tableId，获取包列表
	 * @param dsName 数据源名称
	 * @param tableName 表名称
	 * @return 返回包列表
	 */
	Set<String> getPackageByTableId(String dsName, String tableName);

}
