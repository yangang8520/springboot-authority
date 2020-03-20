package com.sxbang.springbootauthority.service.impl;

import com.sxbang.springbootauthority.dao.PermissionDao;
import com.sxbang.springbootauthority.dto.LoginUser;
import com.sxbang.springbootauthority.model.SysUser;
import com.sxbang.springbootauthority.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.getUser(username);
        if (sysUser == null) {
            throw new AuthenticationCredentialsNotFoundException("用户名不存在");
        } else if (sysUser.getStatus() == SysUser.Status.DISABLED) {
            throw new LockedException("用户被锁定，请联系管理员");
        }

        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUser, loginUser);

        loginUser.setPermissions(permissionDao.listByUserId(sysUser.getId()));
        return loginUser;
    }
}
