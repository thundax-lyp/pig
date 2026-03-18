package com.github.thundax.bacon.sys.api.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 轻量岗位展示对象
 *
 */
@Data
@Schema(description = "岗位展示对象")
public class PostVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "岗位ID")
	private Long postId;

	@Schema(description = "岗位编码")
	private String postCode;

	@Schema(description = "岗位名称")
	private String postName;

}
