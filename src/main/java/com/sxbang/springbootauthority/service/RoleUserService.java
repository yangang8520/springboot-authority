package com.sxbang.springbootauthority.service;

import com.sxbang.springbootauthority.base.result.Results;

public interface RoleUserService {

    Results getSysRoleUserByUserId(Integer userId);
}
