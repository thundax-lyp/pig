package com.pig4cloud.pig.sys.service.api;

import cn.hutool.core.bean.BeanUtil;
import com.pig4cloud.pig.sys.api.dto.SysLogRecordDTO;
import com.pig4cloud.pig.sys.entity.SysLog;
import com.pig4cloud.pig.sys.api.service.LogApi;
import com.pig4cloud.pig.sys.service.SysLogService;
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

	private final SysLogService sysLogService;

	@Override
	public R<Boolean> saveLog(SysLogRecordDTO sysLog) {
		return R.ok(sysLogService.saveLog(BeanUtil.copyProperties(sysLog, SysLog.class)));
	}

}
