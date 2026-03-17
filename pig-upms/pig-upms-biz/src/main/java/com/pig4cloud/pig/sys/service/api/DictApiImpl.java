package com.pig4cloud.pig.sys.service.api;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pig.sys.api.dto.SysDictItemDTO;
import com.pig4cloud.pig.sys.entity.SysDictItem;
import com.pig4cloud.pig.sys.api.service.DictApi;
import com.pig4cloud.pig.sys.service.SysDictItemService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典接口本地实现
 *
 * @author lengleng
 * @date 2026/03/16
 */
@Service
@RequiredArgsConstructor
public class DictApiImpl implements DictApi {

	private final SysDictItemService sysDictItemService;

	@Override
	public R<List<SysDictItemDTO>> getDictByType(String type) {
		return R.ok(sysDictItemService.list(Wrappers.<SysDictItem>query().lambda().eq(SysDictItem::getDictType, type))
			.stream()
			.map(item -> BeanUtil.copyProperties(item, SysDictItemDTO.class))
			.collect(Collectors.toList()));
	}

}
