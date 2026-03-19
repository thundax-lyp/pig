package com.github.thundax.bacon.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.thundax.bacon.sys.entity.UserRole;
import com.github.thundax.bacon.sys.mapper.UserRoleMapper;
import com.github.thundax.bacon.sys.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @since 2017-10-29
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
