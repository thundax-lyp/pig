
package com.github.thundax.bacon.common.security.util;

import cn.hutool.core.util.StrUtil;
import com.github.thundax.bacon.common.core.constant.SecurityConstants;
import com.github.thundax.bacon.common.security.service.BaconUser;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 安全工具类
 *
 */
@UtilityClass
public class SecurityUtils {

	/**
	 * 获取当前安全上下文的认证信息
	 * @return 当前认证信息对象
	 */
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取当前认证用户
	 * @param authentication 认证信息
	 * @return 用户对象，如果认证主体不是BaconUser类型则返回null
	 */
	public BaconUser getUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof BaconUser) {
			return (BaconUser) principal;
		}
		return null;
	}

	/**
	 * 获取当前认证用户
	 * @return 当前认证用户对象，未认证时返回null
	 */
	public BaconUser getUser() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		return getUser(authentication);
	}

	/**
	 * 获取用户角色信息
	 * @return 角色集合
	 */
	public List<Long> getRoles() {
		Authentication authentication = getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		List<Long> roleIds = new ArrayList<>();
		authorities.stream()
			.filter(granted -> StrUtil.startWith(granted.getAuthority(), SecurityConstants.ROLE))
			.forEach(granted -> {
				String id = StrUtil.removePrefix(granted.getAuthority(), SecurityConstants.ROLE);
				roleIds.add(Long.parseLong(id));
			});
		return roleIds;
	}

}
