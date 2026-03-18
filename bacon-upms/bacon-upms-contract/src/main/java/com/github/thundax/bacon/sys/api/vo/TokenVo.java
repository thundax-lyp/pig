package com.github.thundax.bacon.sys.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 令牌展示对象
 *
 */
@Data
@Schema(description = "令牌展示对象")
public class TokenVo {

	@Schema(description = "令牌 ID")
	private String id;

	@Schema(description = "用户ID")
	private Long userId;

	@Schema(description = "客户端ID")
	private String clientId;

	@Schema(description = "用户名")
	private String username;

	@Schema(description = "访问令牌值")
	private String accessToken;

	@Schema(description = "签发时间")
	private String issuedAt;

	@Schema(description = "过期时间")
	private String expiresAt;

}
