package com.sxbang.springbootauthority.service.impl;

import com.sxbang.springbootauthority.base.result.Results;
import com.sxbang.springbootauthority.dao.RoleUserDao;
import com.sxbang.springbootauthority.model.SysRoleUser;
import com.sxbang.springbootauthority.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleUserServiceImpl implements RoleUserService {

    @Autowired
    private RoleUserDao roleUserDao;

    @Override
    public Results getSysRoleUserByUserId(Integer userId) {

        SysRoleUser sysRoleUser = roleUserDao.getSysRoleUserByUserId(userId);
        if (sysRoleUser != null) {
            return Results.success(sysRoleUser);
        }else {
            return Results.success();
        }
    }
}
