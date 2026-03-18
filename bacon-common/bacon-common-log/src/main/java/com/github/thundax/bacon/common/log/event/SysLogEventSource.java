package com.github.thundax.bacon.common.log.event;

import com.github.thundax.bacon.sys.api.dto.LogRecordDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统日志事件源类，继承自日志写入DTO
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysLogEventSource extends LogRecordDTO {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 参数重写成object
	 */
	private Object body;

}
