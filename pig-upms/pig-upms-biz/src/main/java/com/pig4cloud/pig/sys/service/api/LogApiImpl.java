package com.pig4cloud.pig.sys.service.api;

import cn.hutool.core.bean.BeanUtil;
import com.pig4cloud.pig.sys.api.dto.LogRecordDTO;
import com.pig4cloud.pig.sys.entity.Log;
import com.pig4cloud.pig.sys.api.service.LogApi;
import com.pig4cloud.pig.sys.service.LogService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 日志接口本地实现
 *
 * @author lengleng
 * @date 2026/03/16
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
