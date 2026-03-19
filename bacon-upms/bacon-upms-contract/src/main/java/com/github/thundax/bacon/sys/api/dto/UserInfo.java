package com.github.thundax.bacon.sys.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.thundax.bacon.sys.api.vo.DeptVO;
import com.github.thundax.bacon.sys.api.vo.PostVO;
import com.github.thundax.bacon.sys.api.vo.RoleItemVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Spring Security 用户信息对象
 *
 */
@Data
@Schema(description = "Spring Security 用户信息")
public class UserInfo implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Schema(description = "主键ID")
	private Long userId;

	@Schema(description = "用户名")
	private String username;

	/**
	 * 密码
	 */
	@JsonIgnore(value = false)
	private String password;

	/**
	 * 随机盐
	 */
	@JsonIgnore(value = false)
	private String salt;

	@Schema(description = "微信 OpenID")
	private String wxOpenid;

	@Schema(description = "QQ OpenID")
	private String qqOpenid;

	@Schema(description = "Gitee OpenID")
	private String giteeOpenId;

	@Schema(description = "开源中国 OpenID")
	private String oscOpenId;

	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	@Schema(description = "修改时间")
	private LocalDateTime updateTime;

	@Schema(description = "删除标记，1：已删除，0：正常")
	private String delFlag;

	@Schema(description = "锁定标记，0：正常，9：已锁定")
	private String lockFlag;

	@Schema(description = "手机号")
	private String phone;

	@Schema(description = "头像")
	private String avatar;

	@Schema(description = "所属部门")
	private DeptVO dept;

	@Schema(description = "拥有的角色列表")
	private List<RoleItemVO> roleList = new ArrayList<>();

	@Schema(description = "岗位列表")
	private List<PostVO> postList = new ArrayList<>();

	@Schema(description = "昵称")
	private String nickname;

	@Schema(description = "姓名")
	private String name;

	@Schema(description = "邮箱")
	private String email;

	/**
	 * 权限标识集合
	 */
	@Schema(description = "权限标识集合")
	private List<String> permissions = new ArrayList<>();

}
