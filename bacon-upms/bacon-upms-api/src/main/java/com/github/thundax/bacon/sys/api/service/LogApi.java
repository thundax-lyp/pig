package com.github.thundax.bacon.sys.api.service;

import com.github.thundax.bacon.sys.api.dto.LogRecordDTO;
import com.github.thundax.bacon.common.core.util.R;

/**
 * 日志写入接口
 *
 */
public interface LogApi {

	R<Boolean> saveLog(LogRecordDTO sysLog);

}
