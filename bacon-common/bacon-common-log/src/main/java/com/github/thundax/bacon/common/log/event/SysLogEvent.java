
package com.github.thundax.bacon.common.log.event;

import com.github.thundax.bacon.sys.api.dto.LogRecordDTO;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

/**
 * 系统日志事件类
 *
 */
public class SysLogEvent extends ApplicationEvent {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法，根据源SysLog对象创建SysLogEvent
	 * @param source 源SysLog对象
	 */
	public SysLogEvent(LogRecordDTO source) {
		super(source);
	}

}
