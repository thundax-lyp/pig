package com.pig4cloud.pig.sys.service.api;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pig.sys.api.dto.DictItemDTO;
import com.pig4cloud.pig.sys.entity.DictItem;
import com.pig4cloud.pig.sys.api.service.DictApi;
import com.pig4cloud.pig.sys.service.DictItemService;
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

	private final DictItemService sysDictItemService;

	@Override
	public R<List<DictItemDTO>> getDictByType(String type) {
		return R.ok(sysDictItemService.list(Wrappers.<DictItem>query().lambda().eq(DictItem::getDictType, type))
			.stream()
			.map(item -> BeanUtil.copyProperties(item, DictItemDTO.class))
			.collect(Collectors.toList()));
	}

}
