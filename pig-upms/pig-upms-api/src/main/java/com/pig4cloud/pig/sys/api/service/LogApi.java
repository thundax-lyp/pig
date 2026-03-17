package com.pig4cloud.pig.sys.api.service;

import com.pig4cloud.pig.sys.api.dto.SysLogRecordDTO;
import com.pig4cloud.pig.common.core.util.R;

/**
 * 日志写入接口
 *
 * @author lengleng
 * @date 2026/03/16
 */
public interface LogApi {

	R<Boolean> saveLog(SysLogRecordDTO sysLog);

}
