package com.github.thundax.bacon.sys.service.api;

import cn.hutool.core.bean.BeanUtil;
import com.github.thundax.bacon.sys.api.dto.LogRecordDTO;
import com.github.thundax.bacon.sys.entity.Log;
import com.github.thundax.bacon.sys.api.service.LogApi;
import com.github.thundax.bacon.sys.service.LogService;
import com.github.thundax.bacon.common.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 日志接口本地实现
 *
 */
@Service
@RequiredArgsConstructor
public class LogApiImpl implements LogApi {

	private final LogService logService;

	@Override
	public R<Boolean> saveLog(LogRecordDTO sysLog) {
		return R.ok(logService.saveLog(BeanUtil.copyProperties(sysLog, Log.class)));
	}

}
