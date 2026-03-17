package com.pig4cloud.pig.common.log.event;

import com.pig4cloud.pig.sys.api.dto.SysLogRecordDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统日志事件源类，继承自日志写入DTO
 *
 * @author lengleng
 * @date 2025/05/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysLogEventSource extends SysLogRecordDTO {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 参数重写成object
	 */
	private Object body;

}
