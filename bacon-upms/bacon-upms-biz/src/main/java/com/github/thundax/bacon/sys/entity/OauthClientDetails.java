package com.github.thundax.bacon.sys.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * <p>
 * 客户端信息
 * </p>
 *
 * @since 2018-05-15
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
public class OauthClientDetails extends Model<OauthClientDetails> {

	@Serial
	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	private String clientId;

	private String clientSecret;

	private String resourceIds;

	private String scope;

	private String[] authorizedGrantTypes;

	private String webServerRedirectUri;

	private String authorities;

	private Integer accessTokenValidity;

	private Integer refreshTokenValidity;

	private String additionalInformation;

	private String autoapprove;

	@TableLogic
	@TableField(fill = FieldFill.INSERT)
	private String delFlag;

	@TableField(fill = FieldFill.INSERT)
	private String createBy;

	@TableField(fill = FieldFill.UPDATE)
	private String updateBy;

	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@TableField(fill = FieldFill.UPDATE)
	private LocalDateTime updateTime;

}
