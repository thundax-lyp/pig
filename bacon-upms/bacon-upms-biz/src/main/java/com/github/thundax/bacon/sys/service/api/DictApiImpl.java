package com.github.thundax.bacon.sys.service.api;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.thundax.bacon.sys.api.dto.DictItemDTO;
import com.github.thundax.bacon.sys.entity.DictItem;
import com.github.thundax.bacon.sys.api.service.DictApi;
import com.github.thundax.bacon.sys.service.DictItemService;
import com.github.thundax.bacon.common.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典接口本地实现
 *
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
