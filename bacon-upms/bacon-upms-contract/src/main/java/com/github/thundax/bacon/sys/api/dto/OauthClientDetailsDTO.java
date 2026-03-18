package com.github.thundax.bacon.sys.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 客户端详情传输对象
 *
 */
@Data
@Schema(description = "客户端信息")
public class OauthClientDetailsDTO {

	@Schema(description = "id")
	private Long id;

	@NotBlank(message = "client_id 不能为空")
	@Schema(description = "客户端ID")
	private String clientId;

	@NotBlank(message = "client_secret 不能为空")
	@Schema(description = "客户端密钥")
	private String clientSecret;

	@Schema(description = "资源id列表")
	private String resourceIds;

	@NotBlank(message = "scope 不能为空")
	@Schema(description = "作用域")
	private String scope;

	@Schema(description = "授权方式")
	private String[] authorizedGrantTypes;

	@Schema(description = "回调地址")
	private String webServerRedirectUri;

	@Schema(description = "权限列表")
	private String authorities;

	@Schema(description = "访问令牌有效时间")
	private Integer accessTokenValidity;

	@Schema(description = "刷新令牌有效时间")
	private Integer refreshTokenValidity;

	@Schema(description = "扩展信息")
	private String additionalInformation;

	@Schema(description = "是否自动放行")
	private String autoapprove;

	@Schema(description = "删除标记，1：已删除，0：正常")
	private String delFlag;

	@Schema(description = "创建人")
	private String createBy;

	@Schema(description = "修改人")
	private String updateBy;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "更新时间")
	private LocalDateTime updateTime;

}
